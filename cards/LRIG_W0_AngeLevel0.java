package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_AngeLevel0 extends Card {
    
    public LRIG_W0_AngeLevel0()
    {
        setImageSets("WXDi-D02-05LAT", "SPDi01-05");
        
        setOriginalName("アンジュ　レベル０");
        setAltNames("アンジュレベルゼロ Anju Reberu Zero");
        
        setName("en", "Ange, Level 0");
        
        setName("en_fan", "Ange Level 0");
        
		setName("zh_simplified", "安洁 等级0");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANGE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
