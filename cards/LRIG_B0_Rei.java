package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_Rei extends Card {
    
    public LRIG_B0_Rei()
    {
        setImageSets("SPDi03-03");
        
        setOriginalName("レイ");
        setAltNames("Rei");
        
        setName("en", "Rei");
        
		setName("zh_simplified", "令");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REI);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
