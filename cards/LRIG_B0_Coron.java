package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_Coron extends Card {
    
    public LRIG_B0_Coron()
    {
        setImageSets("PR-239");
        
        setOriginalName("ころん");
        setAltNames("コロン Koron");
        
        setName("en", "Coron");
        
		setName("zh_simplified", "科伦");
        setLRIGType(CardLRIGType.ELDORA);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
