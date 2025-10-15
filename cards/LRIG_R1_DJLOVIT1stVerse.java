package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_R1_DJLOVIT1stVerse extends Card {
    
    public LRIG_R1_DJLOVIT1stVerse()
    {
        setImageSets("WXDi-P02-014");
        
        setOriginalName("DJ.LOVIT-1stVerse");
        setAltNames("ディージェーラビットファーストヴァース Dii Jee Rabitto Faasuto Vaasu");
        
        setName("en", "DJ LOVIT - 1st Verse");
        
        setName("en_fan", "DJ.LOVIT - 1st Verse");
        
		setName("zh_simplified", "DJ.LOVIT-1stVerse");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LOVIT);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
