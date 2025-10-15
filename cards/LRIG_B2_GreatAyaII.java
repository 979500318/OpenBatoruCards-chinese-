package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_B2_GreatAyaII extends Card {
    
    public LRIG_B2_GreatAyaII()
    {
        setImageSets("WX15-013", "WXDi-P07-021");
        
        setOriginalName("ぐれーとあーや！Ⅱ");
        setAltNames("グレートアーヤツー Gureeto Aaya Tsuu");
        
        setName("en", "Great Aya! II");
        
        setName("en_fan", "Great Aya! II");
        
		setName("zh_simplified", "卓越亚弥! II");
        setLRIGType(CardLRIGType.AYA);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
