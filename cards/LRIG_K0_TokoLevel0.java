package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K0_TokoLevel0 extends Card {
    
    public LRIG_K0_TokoLevel0()
    {
        setImageSets("WXDi-D02-08LAT", "SPDi01-06");
        
        setOriginalName("とこ　レベル０");
        setAltNames("とこレベルゼロ Toko Reberu Zero");
        
        setName("en", "Toko, Level 0");
        
        setName("en_fan", "Toko Level 0");
        
		setName("zh_simplified", "床 等级0");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TOKO);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
