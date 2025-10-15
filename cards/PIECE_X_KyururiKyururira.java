package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;

public final class PIECE_X_KyururiKyururira extends Card {
    
    public PIECE_X_KyururiKyururira()
    {
        setImageSets("WXDi-P01-008");
        
        setOriginalName("きゅるり☆きゅるりら");
        setAltNames("キュルリキュルリラ Kyururi Kyururira");
        setDescription("jp",
                "カードを１枚引き、[[エナチャージ１]]をする。"
        );
        
        setName("en", "Kyururi ☆ Kyururira");
        setDescription("en",
                "Draw a card and [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Kyururi☆Kyururira");
        setDescription("en_fan",
                "Draw 1 card, and [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "萌☆萌哒");
        setDescription("zh_simplified", 
                "抽1张牌，[[能量填充1]]。\n"
        );

        setType(CardType.PIECE);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            draw(1);
            enerCharge(1);
        }
    }
}
