package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_R0_TawilNollTheInstrumentalist extends Card {
    
    public LRIG_R0_TawilNollTheInstrumentalist()
    {
        setImageSets("WDK14-005", "SPK03-18","SPK04-18","SPK19-04", "PR-K059");
        
        setOriginalName("奏でし者　タウィル＝ノル");
        setAltNames("カナデシモノタウィルノル Kanadeshimono Tauiru Noru");
        
        setName("en", "Tawil-Noll, the Instrumentalist");
        
		setName("zh_simplified", "演奏者 塔维尔=NOLL");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAWIL);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
