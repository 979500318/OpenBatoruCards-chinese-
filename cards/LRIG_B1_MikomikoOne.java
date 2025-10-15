package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B1_MikomikoOne extends Card {
    
    public LRIG_B1_MikomikoOne()
    {
        setImageSets("WXDi-P05-012");
        
        setOriginalName("みこみこ☆いち");
        setAltNames("ミコミコイチ Mikomiko Ichi Mikomiko One");
        
        setName("en", "Mikomiko☆One");
        
        setName("en_fan", "Mikomiko☆One");
        
		setName("zh_simplified", "美琴琴☆壹");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIKOMIKO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
