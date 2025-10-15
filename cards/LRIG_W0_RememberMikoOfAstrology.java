package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W0_RememberMikoOfAstrology extends Card {
    
    public LRIG_W0_RememberMikoOfAstrology()
    {
        setImageSets("WX24-P3-011");
        
        setOriginalName("占星術の巫女　リメンバ");
        setAltNames("センセイジュツノミコリメンバ Senseijutsu no Miko Rimenba");
        
        setName("en", "Remember, Miko of Astrology");
        
		setName("zh_simplified", "占星术的巫女 忆");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REMEMBER);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
