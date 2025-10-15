package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B1_MilulunNano extends Card {
    
    public LRIG_B1_MilulunNano()
    {
        setImageSets(Mask.IGNORE+"SP06-004", "WX04-014", "SP33-021", "WXDi-P10-016","WX25-P1-020");
        
        setOriginalName("ミルルン・ナノ");
        setAltNames("ミルルンナノ Mirurun Nano");
        
        setName("en", "Milulun Nano");
        
        setName("en_fan", "Milulun Nano");
        
		setName("zh_simplified", "米璐璐恩·纳");
        setLRIGType(CardLRIGType.MILULUN);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
