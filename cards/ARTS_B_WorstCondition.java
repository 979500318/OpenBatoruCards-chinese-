package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class ARTS_B_WorstCondition extends Card {

    public ARTS_B_WorstCondition()
    {
        setImageSets("WD16-008", "WDK02-006", "WX10-014");

        setOriginalName("ワースト・コンディション");
        setAltNames("ワーストコンディション Waasuto Kondishon");
        setDescription("jp",
                "このアーツは対戦相手の手札が０枚の場合にしか使用できない。\n\n" +
                "対戦相手のシグニを２体まで対象とし、それらをバニッシュする。"
        );

        setName("en", "Worst Condition");
        setDescription("en",
                "This ARTS can only be used if there are 0 cards in your opponent's hand.\n\n" +
                "Target up to 2 of your opponent's SIGNI, and banish them."
        );

        setName("es", "Worst Condition");
        setDescription("es",
                "Este ARTS solo puede ser usado si no hay cartas en la mano oponente.\n\n" +
                "Selecciona hasta 2 SIGNI oponentes y desvánecelas."
        );

        setName("zh_simplified", "最坏·情况");
        setDescription("zh_simplified", 
                "这张必杀只有在对战对手的手牌在0张的场合才能使用。\n" +
                "对战对手的精灵2只最多作为对象，将这些破坏。"
        );

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2) + Cost.colorless(1));
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
            return getHandCount(getOpponent()) == 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.BANISH).OP().SIGNI());
            banish(data);
        }
    }
}

