package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_G2_VJWOLF2ndVerse extends Card {
    
    public LRIG_G2_VJWOLF2ndVerse()
    {
        setImageSets("WXDi-P03-015");
        
        setOriginalName("VJ.WOLF-2ndVerse");
        setAltNames("ブイジェーウルフセカンドヴァース Bui Jee Urufu Sekando Vaasu");
        
        setName("en", "VJ WOLF - 2nd Verse");
        
        setName("en_fan", "VJ.WOLF - 2nd Verse");
        
		setName("zh_simplified", "VJ.WOLF-2ndVerse");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.WOLF);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
