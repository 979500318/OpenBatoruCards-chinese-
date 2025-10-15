package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B0_Milulun extends Card {
    
    public LRIG_B0_Milulun()
    {
        setImageSets("PR-169", Mask.IGNORE+"WX11-TR03", Mask.IGNORE+"WX19-BM05");
        
        setOriginalName("ミルルン");
        setAltNames("Mirurun");
        
        setName("en", "Milulun");
        
		setName("zh_simplified", "米璐璐恩");
        setLRIGType(CardLRIGType.MILULUN);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
