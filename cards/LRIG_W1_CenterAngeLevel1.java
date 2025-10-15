package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W1_CenterAngeLevel1 extends Card {
    
    public LRIG_W1_CenterAngeLevel1()
    {
        setImageSets("WXDi-D02-11A");
        
        setOriginalName("【センター】アンジュ　レベル１");
        setAltNames("センターアンジュレベルイチ Sentaa Anju Reberu Ichi Center Ange");
        
        setName("en", "[Center] Ange, Level 1");
        
        setName("en_fan", "[Center] Ange Level 1");
        
		setName("zh_simplified", "【核心】安洁 等级1");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANGE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
