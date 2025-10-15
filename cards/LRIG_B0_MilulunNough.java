package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B0_MilulunNough extends Card {
    
    public LRIG_B0_MilulunNough()
    {
        setImageSets(Mask.IGNORE+"SP06-005", Mask.IGNORE+"SP08-002","SP16-004","SP35-014", "PR-064","PR-080",Mask.IGNORE+"PR-132", "WXDi-P10-015","WX25-P1-019", "SPDi01-57", Mask.IGNORE+"SPDi42-4P");
        
        setOriginalName("ミルルン・ノット");
        setAltNames("ミルルンノット Mirurun Notto");
        
        setName("en", "Milulun Nought");
        
        setName("en_fan", "Milulun Nough");
        
		setName("zh_simplified", "米璐璐恩·节");
        setLRIGType(CardLRIGType.MILULUN);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
