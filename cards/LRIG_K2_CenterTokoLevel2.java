package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_K2_CenterTokoLevel2 extends Card {
    
    public LRIG_K2_CenterTokoLevel2()
    {
        setImageSets("WXDi-D02-15T");
        
        setOriginalName("【センター】とこ　レベル２");
        setAltNames("センターとこレベルニ Sentaa Toko Reberu Ni Center Toko");
        
        setName("en", "[Center] Toko, Level 2");
        
        setName("en_fan", "[Center] Toko Level 2");
        
		setName("zh_simplified", "【核心】床 等级2");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TOKO);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
