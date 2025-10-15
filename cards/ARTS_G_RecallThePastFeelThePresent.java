package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class ARTS_G_RecallThePastFeelThePresent extends Card {

    public ARTS_G_RecallThePastFeelThePresent()
    {
        setImageSets("WX24-P3-008", "WX24-P3-008U");

        setOriginalName("今昔之感");
        setAltNames("レトロスペクティブ Retorosupekutibu Retrospective");
        setDescription("jp",
                "このアーツを使用する際、手札からパワー10000以上のシグニを３枚まで捨てる。このアーツの使用コストはこの方法で捨てたシグニ１枚につき%G減る。\n\n" +
                "&E４枚以上@0このターン、対戦相手のシグニ１体がアタックしたとき、そのアタックを無効にする。"
        );
        
        setName("en", "Recall the Past, Feel the Present");
        setDescription("en",
                "While using this ARTS, you may discard up to 3 SIGNI with power 10000 or more from your hand. If you do, the use cost of this ARTS is reduced by %G for each discarded this way SIGNI.\n\n" +
                "&E4 or more@0 This turn, whenever your opponent's SIGNI attacks, disable that attack."
        );

		setName("zh_simplified", "今昔之感");
        setDescription("zh_simplified", 
                "这张必杀使用时，从手牌把力量10000以上的精灵3张最多舍弃。这张必杀的使用费用依据这个方法舍弃的精灵的数量，每有1张就减%G。\n" +
                "&E4张以上@0这个回合，当对战对手的精灵1只攻击时，那次攻击无效。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 4));
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
            arts.setReductionCost(new DiscardCost(0,3, new TargetFilter().SIGNI().withPower(10000,0)), Cost.color(CardColor.GREEN, 1));
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
                addPlayerRuleCheck(PlayerRuleCheckType.CAN_BE_ATTACKED, getOwner(), ChronoDuration.turnEnd(), data ->
                    CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
                );
            }
        }
    }
}
