package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_R2_DJLOVIT2ndVerse extends Card {
    
    public LRIG_R2_DJLOVIT2ndVerse()
    {
        setImageSets("WXDi-P02-015");
        
        setOriginalName("DJ.LOVIT-2ndVerse");
        setAltNames("ディージェーラビットセカンドヴァース Dii Jee Rabitto Sekando Vaasu");
        
        setName("en", "DJ LOVIT - 2nd Verse");
        
        setName("en_fan", "DJ.LOVIT - 2nd Verse");
        
		setName("zh_simplified", "DJ.LOVIT-2ndVerse");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LOVIT);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
