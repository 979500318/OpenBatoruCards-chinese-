package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K1_CenterTokoLevel1 extends Card {
    
    public LRIG_K1_CenterTokoLevel1()
    {
        setImageSets("WXDi-D02-14T");
        
        setOriginalName("【センター】とこ　レベル１");
        setAltNames("センターとこレベルイチ Sentaa Toko Reberu Ichi Center Toko");
        
        setName("en", "[Center] Toko, Level 1");
        
        setName("en_fan", "[Center] Toko Level 1");
        
		setName("zh_simplified", "【核心】床 等级1");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TOKO);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
