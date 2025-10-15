package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W1_CenterLizeLevel1 extends Card {
    
    public LRIG_W1_CenterLizeLevel1()
    {
        setImageSets("WXDi-D02-02L");
        
        setOriginalName("【センター】リゼ　レベル１");
        setAltNames("センターリゼレベルイチ Sentaa Rize Reberu Ichi Center Lize");
        
        setName("en", "[Center] Lize, Level 1");
        
        setName("en_fan", "[Center] Lize Level 1");
        
		setName("zh_simplified", "【核心】莉泽 等级1");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LIZE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
