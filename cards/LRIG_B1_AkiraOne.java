package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B1_AkiraOne extends Card {
    
    public LRIG_B1_AkiraOne()
    {
        setImageSets("WX24-P2-020");
        
        setOriginalName("あきら☆いち");
        setAltNames("アキライチ Akira Ichi");
        
        setName("en", "Akira☆One");
        
		setName("zh_simplified", "晶☆壹");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AKIRA);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
