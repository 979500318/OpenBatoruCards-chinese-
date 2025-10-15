package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_W2_RememberNoonMikoOfAstrology extends Card {
    
    public LRIG_W2_RememberNoonMikoOfAstrology()
    {
        setImageSets("WX24-P3-013");
        
        setOriginalName("占星術の巫女　リメンバ・ヌーン");
        setAltNames("センセイジュツノミコリメンバヌーン Senseijutsu no Miko Rimenba Nuun");
        
        setName("en", "Remember Noon, Miko of Astrology");
        
		setName("zh_simplified", "占星术的巫女 忆·正午");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REMEMBER);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
