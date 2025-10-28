package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class ARTS_B_BetterCondition extends Card {

    public ARTS_B_BetterCondition()
    {
        setImageSets("WXK01-020");

        setOriginalName("ベター・コンディション");
        setAltNames("ベターコンディション Betaa Kondishon");
        setDescription("jp",
                "このアーツはあなたの手札が１枚以下の場合にしか使用できない。\n\n" +
                "対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Better Condition");
        setDescription("en",
                "This ARTS can only be used if you there are 1 or less cards in your hand.\n\n" +
                "Target 1 of your opponent's SIGNI, and banish it."
        );

        setName("zh_simplified", "更好·情况");
        setDescription("zh_simplified", 
                "这张必杀只有在你的手牌在1张以下的场合才能使用。\n" +
                "对战对手的精灵1只作为对象，将其破坏。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setCondition(this::onARTSEffCond);
        }

        private ConditionState onARTSEffCond()
        {
            return getHandCount(getOwner()) <= 1 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
    }
}
