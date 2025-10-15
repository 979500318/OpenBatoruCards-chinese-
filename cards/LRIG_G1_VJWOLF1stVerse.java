package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G1_VJWOLF1stVerse extends Card {
    
    public LRIG_G1_VJWOLF1stVerse()
    {
        setImageSets("WXDi-P03-014");
        
        setOriginalName("VJ.WOLF-1stVerse");
        setAltNames("ブイジェーウルフファーストヴァース Bui Jee Urufu Faasuto Vaasu");
        
        setName("en", "VJ WOLF - 1st Verse");
        
        setName("en_fan", "VJ.WOLF - 1st Verse");
        
		setName("zh_simplified", "VJ.WOLF-1stVerse");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.WOLF);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
