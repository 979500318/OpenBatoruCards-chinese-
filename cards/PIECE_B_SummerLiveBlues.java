package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
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

public final class PIECE_B_SummerLiveBlues extends Card {
    
    public PIECE_B_SummerLiveBlues()
    {
        setImageSets("WXDi-P04-003");
        
        setOriginalName("サマーライブブルーズ");
        setAltNames("Samaa Raibu Buruuzu");
        setDescription("jp",
                "=U あなたの場に青のルリグが２体上いる\n\n" +
                "カードを２枚引く。その後、対戦相手のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );
        
        setName("en", "Summer Live Blues");
        setDescription("en",
                "=U You have two or more blue LRIG on your field.\n\n" +
                "Draw two cards. Then, put target SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Summer Live Blues");
        setDescription("en_fan",
                "=U There are 2 or more blue LRIGs on your field\n\n" +
                "Draw 2 cards. Then, target 1 of your opponent's SIGNI, and put it on the bottom of their deck."
        );
        
		setName("zh_simplified", "夏日蓝调");
        setDescription("zh_simplified", 
                "=U你的场上的蓝色的分身在2只以上\n" +
                "抽2张牌。然后，对战对手的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            return new TargetFilter().own().anyLRIG().withColor(CardColor.BLUE).getValidTargetsCount() >= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            draw(2);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
