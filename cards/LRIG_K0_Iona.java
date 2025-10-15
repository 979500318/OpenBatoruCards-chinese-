package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K0_Iona extends Card {
    
    public LRIG_K0_Iona()
    {
        setImageSets("PR-067", Mask.IGNORE+"WX11-TR05");
        
        setOriginalName("イオナ");
        setAltNames("Iona");
        
        setName("en", "Iona");
        
		setName("zh_simplified", "伊绪奈");
        setLRIGType(CardLRIGType.IONA);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
