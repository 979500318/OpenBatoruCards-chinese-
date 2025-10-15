package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_AkiraZero extends Card {
    
    public LRIG_B0_AkiraZero()
    {
        setImageSets("WX24-P2-019");
        
        setOriginalName("あきら☆ぜろ");
        setAltNames("アキラゼロ Akira Zero");
        
        setName("en", "Akira☆Zero");
        
		setName("zh_simplified", "晶☆零");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AKIRA);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
