package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.Cost;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B2_MilulunPico extends Card {
    
    public LRIG_B2_MilulunPico()
    {
        setImageSets(Mask.IGNORE+"SP06-003", "WX04-013", "SP33-020", "WXDi-P10-017","WX25-P1-021");
        
        setOriginalName("ミルルン・ピコ");
        setAltNames("ミルルンピコ Mirurun Piko");
        
        setName("en", "Milulun Pico");
        
        setName("en_fan", "Milulun Pico");
        
		setName("zh_simplified", "米璐璐恩·皮");
        setLRIGType(CardLRIGType.MILULUN);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
