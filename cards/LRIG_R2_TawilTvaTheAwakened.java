package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_R2_TawilTvaTheAwakened extends Card {
    
    public LRIG_R2_TawilTvaTheAwakened()
    {
        setImageSets("WXDi-P00-008");
        
        setOriginalName("目醒めし者　タウィル＝トヴォ");
        setAltNames("メザメシモノタウィルトヴォ Mezameshimono Tauiru Tovo");
        
        setName("en", "Tawil =Två=, Awakened One");
        
        setName("en_fan", "Tawil-Två, the Awakened");
        
		setName("zh_simplified", "目醒者 塔维尔=TVA");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAWIL);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
