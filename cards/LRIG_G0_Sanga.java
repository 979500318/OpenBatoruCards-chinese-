package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G0_Sanga extends Card {
    
    public LRIG_G0_Sanga()
    {
        setImageSets("SPDi03-12");
        
        setOriginalName("サンガ");
        setAltNames("Sanga");
        
        setName("en", "Sanga");
        
		setName("zh_simplified", "山河");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SANGA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
