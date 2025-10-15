package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_AzusaShirasu extends Card {
    
    public LRIG_W0_AzusaShirasu()
    {
        setImageSets("WXDi-CP02-011", "SPDi31-01","SPDi32-01");
        
        setOriginalName("白洲アズサ");
        setAltNames("シラスアズサ Shirasu Azusa");
        
        setName("en", "Shirasu Azusa");
        
        
        setName("en_fan", "Azusa Shirasu");
        
		setName("zh_simplified", "白洲梓");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AZUSA);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

