package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_CC extends Card {
    
    public LRIG_B0_CC()
    {
        setImageSets("WXDi-CP02-030");
        
        setOriginalName("Ｃ＆Ｃ");
        setAltNames("クリーニングアンドクリアリング Kuriiningu ando Kuriaringu Cleaning and Clearing Cleaning & Clearing");
        
        setName("en", "C&C");
        
        
        setName("en_fan", "C&C");
        
		setName("zh_simplified", "C＆C");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.CC);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

