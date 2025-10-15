package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_Madoka extends Card {
    
    public LRIG_B0_Madoka()
    {
        setImageSets("SPDi03-11");
        
        setOriginalName("マドカ");
        setAltNames("Madoka");
        
        setName("en", "Madoka");
        
		setName("zh_simplified", "円");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MADOKA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
