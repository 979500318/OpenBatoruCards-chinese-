package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_Umr extends Card {
    
    public LRIG_B0_Umr()
    {
        setImageSets("SPK22-02", "SPDi02-01");
        
        setOriginalName("ウムル");
        setAltNames("Umuru");
        
        setName("en", "Umr");
        
		setName("zh_simplified", "乌姆尔");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.UMR);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
