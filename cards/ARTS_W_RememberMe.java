package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class ARTS_W_RememberMe extends Card {

    public ARTS_W_RememberMe()
    {
        setImageSets("WX24-P3-002", "WX24-P3-002U");

        setOriginalName("クリスタル・エクスプロージョン");
        setAltNames("クリスタルエクスプロージョン Kurisutaru Ekusupuroojon");
        setDescription("jp",
                "このアーツを使用する際、手札から#Gを持つシグニを好きな枚数捨てる。このアーツの使用コストはこの方法で捨てたカード１枚につき%W %W減る。\n\n" +
                "&E４枚以上@0ターン終了時まで、対戦相手のすべてのシグニは@>@C：アタックできない。@@を得る。"
        );

        setName("en", "Remember Me");
        setDescription("en",
                "While using this ARTS, you may discard any number of #G @[Guard]@ cards from your hand. If you do, the use cost of this ARTS is reduced by %W %W for each card discarded this way.\n\n" +
                "&E4 or more@0 Until end of turn, all of your opponent's SIGNI gain:\n" +
                "@>@C: Can't attack.@@"
        );

		setName("zh_simplified", "记忆·真我");
        setDescription("zh_simplified", 
                "这张必杀使用时，从手牌把持有#G的精灵任意张数舍弃。这张必杀的使用费用依据这个方法舍弃的牌的数量，每有1张就减%W %W。\n" +
                "&E4张以上@0直到回合结束时为止，对战对手的全部的精灵得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 5));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setCondition(this::onARTSEffCond);
            arts.setReductionCost(new DiscardCost(0,AbilityConst.MAX_UNLIMITED, new TargetFilter().withState(CardStateFlag.CAN_GUARD)), Cost.color(CardColor.WHITE, 2));
            arts.setRecollect(4);
        }
        
        private ConditionState onARTSEffCond()
        {
            return arts.isRecollectFulfilled() ? ConditionState.OK : ConditionState.WARN;
        }
        private void onARTSEff()
        {
            if(arts.isRecollectFulfilled())
            {
                forEachSIGNIOnField(getOpponent(), cardIndex -> attachAbility(cardIndex, new StockAbilityCantAttack(), ChronoDuration.turnEnd()));
            }
        }
    }
}
