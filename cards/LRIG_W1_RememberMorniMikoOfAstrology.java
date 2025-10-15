package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W1_RememberMorniMikoOfAstrology extends Card {
    
    public LRIG_W1_RememberMorniMikoOfAstrology()
    {
        setImageSets("WX24-P3-012");
        
        setOriginalName("占星術の巫女　リメンバ・モーニ");
        setAltNames("センセイジュツノミコリメンバモーニ Senseijutsu no Miko Rimenba Mooni");
        
        setName("en", "Remember Morni, Miko of Astrology");
        
		setName("zh_simplified", "占星术的巫女 忆·晨星");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REMEMBER);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
