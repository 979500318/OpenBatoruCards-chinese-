package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_LION extends Card {
    
    public LRIG_W0_LION()
    {
        setImageSets("SPDi03-04", "SPDi34-20");
        
        setOriginalName("ＬＩＯＮ");
        setAltNames("リオン Rion");
        
        setName("en", "LION");
        
		setName("zh_simplified", "LION");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
