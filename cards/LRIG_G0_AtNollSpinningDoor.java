package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G0_AtNollSpinningDoor extends Card {
    
    public LRIG_G0_AtNollSpinningDoor()
    {
        setImageSets("WXDi-D01-001","WXDi-P16-026","WX25-P1-023","SPDi44-01", "SPDi01-01","SPDi05-01","SPDi23-04");
        
        setOriginalName("紡ぎし扉　アト＝ノル");
        setAltNames("ツムギシトビラアトノル Tsumugishi Tobira Ato Noru");
        
        setName("en", "At =Noll=, the Opened Gate");
        
        setName("en_fan", "At-Noll, Spinning Door");
        
		setName("zh_simplified", "纺转扉 亚特=NOLL");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AT);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
