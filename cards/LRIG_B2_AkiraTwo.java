package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_B2_AkiraTwo extends Card {
    
    public LRIG_B2_AkiraTwo()
    {
        setImageSets("WX24-P2-021");
        
        setOriginalName("あきら☆にっ");
        setAltNames("アキラニッ Akira Nii");
        
        setName("en", "Akira☆Two");
        
		setName("zh_simplified", "晶☆贰");
        setLRIGType(CardLRIGType.AKIRA);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
