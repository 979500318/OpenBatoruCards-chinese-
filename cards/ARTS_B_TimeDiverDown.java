package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;

public final class ARTS_B_TimeDiverDown extends Card {

    public ARTS_B_TimeDiverDown()
    {
        setImageSets("WX24-P3-006", "WX24-P3-006U");

        setOriginalName("タイム・ダイバー・ダウン");
        setAltNames("タイムダイバーダウン Taimu Daibaa Daun");
        setDescription("jp",
                "このアーツを使用する際、手札から青のカードを３枚まで捨てる。このアーツの使用コストはこの方法で捨てたカード１枚につき%B減る。\n\n" +
                "&E４枚以上@0対戦相手のすべてのシグニをダウンする。"
        );

        setName("en", "Time Diver Down");
        setDescription("en",
                "While using this ARTS, you may discard up to 3 blue cards from your hand. If you do, the use cost of this ARTS is reduced by %B for each discarded this way card.\n\n" +
                "&E4 or more@0 Down all of your opponent's SIGNI."
        );

		setName("zh_simplified", "时间·潜水·下降");
        setDescription("zh_simplified", 
                "这张必杀使用时，从手牌把蓝色的牌3张最多舍弃。这张必杀的使用费用依据这个方法舍弃的牌的数量，每有1张就减%B。\n" +
                "&E4张以上@0对战对手的全部的精灵#D。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 5));
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
            arts.setReductionCost(new DiscardCost(0,3, new TargetFilter().withColor(CardColor.BLUE)), Cost.color(CardColor.BLUE, 1));
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
                down(getSIGNIOnField(getOpponent()));
            }
        }
    }
}
