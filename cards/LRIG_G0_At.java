package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G0_At extends Card {
    
    public LRIG_G0_At()
    {
        setImageSets("PR-Di002", "SPDi34-24");
        
        setOriginalName("アト");
        setAltNames("アト Ato");
        
        setName("en", "At");
        
        setName("en_fan", "At");
        
		setName("zh_simplified", "亚特");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AT);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
