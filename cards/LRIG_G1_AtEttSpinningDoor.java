package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G1_AtEttSpinningDoor extends Card {
    
    public LRIG_G1_AtEttSpinningDoor()
    {
        setImageSets("WXDi-D01-002", "WXDi-P16-027", "WX25-P1-024","SPDi44-02");
        
        setOriginalName("紡ぎし扉　アト＝エット");
        setAltNames("ツムギシトビラアトエット Tsumugishi Tobira Ato Etto");
        
        setName("en", "At =Ett=, the Opened Door");
        
        setName("en_fan", "At-Ett, Spinning Door");
        
		setName("zh_simplified", "纺转扉 亚特=ETT");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AT);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
