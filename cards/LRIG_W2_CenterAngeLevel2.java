package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_W2_CenterAngeLevel2 extends Card {
    
    public LRIG_W2_CenterAngeLevel2()
    {
        setImageSets("WXDi-D02-12A");
        
        setOriginalName("【センター】アンジュ　レベル２");
        setAltNames("センターアンジュレベルニ Sentaa Anju Reberu Ni Center Ange");
        
        setName("en", "[Center] Ange, Level 2");
        
        setName("en_fan", "[Center] Ange Level 2");
        
		setName("zh_simplified", "【核心】安洁 等级2");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANGE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
