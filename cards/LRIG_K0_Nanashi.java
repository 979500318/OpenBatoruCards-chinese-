package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K0_Nanashi extends Card {
    
    public LRIG_K0_Nanashi()
    {
        setImageSets("PR-353","SPK22-08", "WX16_JR-007", Mask.IGNORE+"WX17-CL03","WX18-BB21",Mask.IGNORE+"WX21-GC10");
        
        setOriginalName("ナナシ");
        setAltNames("Nanashi");
        
        setName("en", "Nanashi");
        
		setName("zh_simplified", "无名");
        setLRIGType(CardLRIGType.NANASHI);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setCoins(+2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
