package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B0_Soui extends Card {
    
    public LRIG_B0_Soui()
    {
        setImageSets(Mask.IGNORE+"WX13-TR03");
        
        setOriginalName("ソウイ");
        setAltNames("ソウイ Soui");
        
        setName("en", "Soui");
        
		setName("zh_simplified", "SOUI");
        setLRIGType(CardLRIGType.SOUI);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
