package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_LizeLevel0 extends Card {
    
    public LRIG_W0_LizeLevel0()
    {
        setImageSets("WXDi-D02-01LAT", "SPDi01-04", "PR-Di008");
        
        setOriginalName("リゼ　レベル０");
        setAltNames("リゼレベルゼロ Rize Reberu Zero");
        
        setName("en", "Lize, Level 0");
        
        setName("en_fan", "Lize Level 0");
        
		setName("zh_simplified", "莉泽 等级0");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LIZE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
