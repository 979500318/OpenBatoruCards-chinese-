package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_Akino extends Card {
    
    public LRIG_W0_Akino()
    {
        setImageSets("SPDi03-02","SPDi34-11");
        
        setOriginalName("アキノ");
        setAltNames("Akino");
        
        setName("en", "Akino");
        
		setName("zh_simplified", "昭乃");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AKINO);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
