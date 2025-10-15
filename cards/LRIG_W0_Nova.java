package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_Nova extends Card {
    
    public LRIG_W0_Nova()
    {
        setImageSets("SPDi03-08");
        
        setOriginalName("ノヴァ");
        setAltNames("Nova");
        
        setName("en", "Nova");
        
		setName("zh_simplified", "超");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NOVA);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
