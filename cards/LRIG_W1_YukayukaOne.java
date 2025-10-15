package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W1_YukayukaOne extends Card {
    
    public LRIG_W1_YukayukaOne()
    {
        setImageSets("WXDi-P08-011");
        
        setOriginalName("ゆかゆか☆いち");
        setAltNames("ユカユカイチ Yukayuka Ichi Yukayuka One");
        
        setName("en", "Yukayuka☆One");
        
        setName("en_fan", "Yukayuka☆One");
        
		setName("zh_simplified", "由香香☆壹");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUKAYUKA);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
