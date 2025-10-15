package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_G2_AtTvaSpinningDoor extends Card {
    
    public LRIG_G2_AtTvaSpinningDoor()
    {
        setImageSets("WXDi-D01-003", "WXDi-P16-028", "WX25-P1-025","SPDi44-03");
        
        setOriginalName("紡ぎし扉　アト＝トヴォ");
        setAltNames("ツムギシトビラアトトヴォ Tsumugishi Tobira Ato Tovo");
        
        setName("en", "At =Två=, the Opened Gate");
        
        setName("en_fan", "At-Två, Spinning Door");
        
		setName("zh_simplified", "纺转扉 亚特=TVA");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AT);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
