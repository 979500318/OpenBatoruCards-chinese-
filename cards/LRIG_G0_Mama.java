package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_G0_Mama extends Card {
    
    public LRIG_G0_Mama()
    {
        setImageSets("PR-409", "WX16_JR-006", "WX17-CL09", Mask.IGNORE+"WX19-BM08","WX21-GC09");
        
        setOriginalName("ママ");
        setAltNames("Mama");
        
        setName("en", "Mama");
        
		setName("zh_simplified", "妈妈");
        setLRIGType(CardLRIGType.MAMA);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCoins(+4);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
