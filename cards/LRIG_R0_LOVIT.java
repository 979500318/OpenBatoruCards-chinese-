package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_R0_LOVIT extends Card {
    
    public LRIG_R0_LOVIT()
    {
        setImageSets("SPDi03-05");
        
        setOriginalName("ＬＯＶＩＴ");
        setAltNames("ラビット Rabitto");
        
        setName("en", "LOVIT");
        
		setName("zh_simplified", "LOVIT");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LOVIT);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
