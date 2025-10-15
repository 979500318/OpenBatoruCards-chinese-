package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_UmrNollWielderOfTheWorldInstrumentKey extends Card {
    
    public LRIG_B0_UmrNollWielderOfTheWorldInstrumentKey()
    {
        setImageSets("WDK09-005", "SPK03-13","SPK04-13","SPK15-05","SPK16-1A","SPK18-01", "PR-K052");
        
        setOriginalName("奏世の鍵主　ウムル＝ノル");
        setAltNames("ソウセイノカギヌシウムルノル Soosei no Kaginushi Umuru Noru");
        
        setName("en", "Umr-Noll, Wielder of the World Instrument Key");
        
		setName("zh_simplified", "奏世的键主 乌姆尔=NOLL");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.UMR);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
