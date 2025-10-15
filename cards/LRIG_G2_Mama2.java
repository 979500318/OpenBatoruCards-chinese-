package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_G2_Mama2 extends Card {
    
    public LRIG_G2_Mama2()
    {
        setImageSets("WX16-019", "WXDi-P09-021");
        
        setOriginalName("ママ♥２");
        setAltNames("ママツー Mama Tsuu");
        
        setName("en", "Mama ♥ 2");
        
        setName("en_fan", "Mama♥2");
        
		setName("zh_simplified", "妈妈♥2");
        setLRIGType(CardLRIGType.MAMA);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
