package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G0_Bang extends Card {
    
    public LRIG_G0_Bang()
    {
        setImageSets("SPDi03-09");
        
        setOriginalName("バン");
        setAltNames("Ban");
        
        setName("en", "Bang");
        
		setName("zh_simplified", "梆");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.BANG);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
