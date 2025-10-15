package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class PIECE_G_DANCEINTHELANCE extends Card {
    
    public PIECE_G_DANCEINTHELANCE()
    {
        setImageSets("WXDi-P05-002");
        
        setOriginalName("DANCE IN THE LANCE");
        setAltNames("ダンスインザランス Dansu in za Ransu");
        setDescription("jp",
                "=U あなたの場に緑のルリグが２体以上いる\n\n" +
                "以下の２つから１つを選ぶ。\n" +
                "$$1[[エナチャージ４]]をする。その後、あなたのエナゾーンからカードを２枚まで対象とし、それらを手札に加える。\n" +
                "$$2あなたの緑のシグニを２体まで対象とし、ターン終了時まで、それらは[[ランサー]]を得る。"
        );
        
        setName("en", "DANCE IN THE LANCE");
        setDescription("en",
                "=U You have two or more green LRIG on your field.\n\n" +
                "Choose one of the following.\n" +
                "$$1 [[Ener Charge 4]]. Then, add up to two target cards from your Ener Zone to your hand.\n" +
                "$$2 Up to two target green SIGNI on your field gain [[Lancer]] until end of turn."
        );
        
        setName("en_fan", "DANCE IN THE LANCE");
        setDescription("en_fan",
                "=U There are 2 or more green LRIGs on your field\n\n" +
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 [[Ener Charge 4]]. Then, target up to 2 cards from your ener zone, and add them to your hand.\n" +
                "$$2 Target up to 2 of your green SIGNI, and until end of turn, they gain [[Lancer]]."
        );
        
		setName("zh_simplified", "DANCE IN THE LANCE");
        setDescription("zh_simplified", 
                "=U你的场上的绿色的分身在2只以上\n" +
                "从以下的2种选1种。\n" +
                "$$1 [[能量填充4]]。然后，从你的能量区把牌2张最多作为对象，将这些加入手牌。\n" +
                "$$2 你的绿色的精灵2只最多作为对象，直到回合结束时为止，这些得到[[枪兵]]。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
            piece.setModeChoice(1);
        }
        
        private ConditionState onPieceEffCond()
        {
            return new TargetFilter().own().anyLRIG().withColor(CardColor.GREEN).getValidTargetsCount() >= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            if(piece.getChosenModes() == 1)
            {
                enerCharge(4);
                
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromEner());
                addToHand(data);
            } else {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.GREEN));
                if(data.get() != null) for(int i=0;i<data.size();i++) attachAbility(data.get(i), new StockAbilityLancer(), ChronoDuration.turnEnd());
            }
        }
    }
}
