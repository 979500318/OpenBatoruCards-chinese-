package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_R0_TawilNollTheAwakaned extends Card {
    
    public LRIG_R0_TawilNollTheAwakaned()
    {
        setImageSets("WXDi-D01-005", "SPDi01-02","SPDi11-06","SPDi23-02");
        
        setOriginalName("目醒めし者　タウィル＝ノル");
        setAltNames("メザメシモノタウィルノル Mezameshimono Tauiru Noru");
        
        setName("en", "Tawil =Noll=, Awakened One");
        
        setName("en_fan", "Tawil-Noll, the Awakened");
        
		setName("zh_simplified", "目醒者 塔维尔=NOLL");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAWIL);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
