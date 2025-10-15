package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_Lize extends Card {
    
    public LRIG_W0_Lize()
    {
        setImageSets("SPDi02-02");
        
        setOriginalName("リゼ");
        setAltNames("Rize");
        
        setName("en", "Lize");
        
        setName("en_fan", "Lize");
        
		setName("zh_simplified", "莉泽");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LIZE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
