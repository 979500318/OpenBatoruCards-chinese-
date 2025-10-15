package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_W2_CenterLizeLevel2 extends Card {
    
    public LRIG_W2_CenterLizeLevel2()
    {
        setImageSets("WXDi-D02-03L");
        
        setOriginalName("【センター】リゼ　レベル２");
        setAltNames("センターリゼレベルニ Sentaa Rize Reberu Ni Center Lize");
        
        setName("en", "[Center] Lize, Level 2");
        
        setName("en_fan", "[Center] Lize Level 2");
        
		setName("zh_simplified", "【核心】莉泽 等级2");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LIZE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
