package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_R0_Hirana extends Card {
    
    public LRIG_R0_Hirana()
    {
        setImageSets("SPDi03-01");
        
        setOriginalName("ヒラナ");
        setAltNames("Hirana");
        
        setName("en", "Hirana");
        
		setName("zh_simplified", "平和");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HIRANA);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
