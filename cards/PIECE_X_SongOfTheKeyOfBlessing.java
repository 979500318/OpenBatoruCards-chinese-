package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;

public final class PIECE_X_SongOfTheKeyOfBlessing extends Card {
    
    public PIECE_X_SongOfTheKeyOfBlessing()
    {
        setImageSets("WXDi-P00-005", "WXDi-D07-012");
        
        setOriginalName("祝福の鍵の音");
        setAltNames("シュクフクノカギノネ Shukufuku no Kagi no Ne");
        setDescription("jp",
                "カードを３枚引き、手札を２枚捨てる。"
        );
        
        setName("en", "Chime of the Blessed Key");
        setDescription("en",
                "Draw three cards, and discard two cards."
        );
        
        setName("en_fan", "Song of the Key of Blessing");
        setDescription("en_fan",
                "Draw 3 cards, and discard 2 cards from your hand."
        );
        
		setName("zh_simplified", "祝福的键的音");
        setDescription("zh_simplified", 
                "抽3张牌，手牌2张舍弃。\n"
        );

        setType(CardType.PIECE);
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
            
            registerPieceAbility(this::onPieceEff);
        }
        
        private void onPieceEff()
        {
            draw(3);
            discard(2);
        }
    }
}
