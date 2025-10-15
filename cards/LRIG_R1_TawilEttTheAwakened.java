package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_R1_TawilEttTheAwakened extends Card {
    
    public LRIG_R1_TawilEttTheAwakened()
    {
        setImageSets("WXDi-P00-007");
        
        setOriginalName("目醒めし者　タウィル＝エット");
        setAltNames("メザメシモノタウィルエット Mezameshimono Tauiru Etto");
        
        setName("en", "Tawil =Ett=, Awakened One");
        
        setName("en_fan", "Tawil-Ett, the Awakened");
        
		setName("zh_simplified", "目醒者 塔维尔=ETT");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAWIL);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
