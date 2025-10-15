package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_W2_MCLION2ndVerse extends Card {
    
    public LRIG_W2_MCLION2ndVerse()
    {
        setImageSets("WXDi-D04-003");
        
        setOriginalName("MC.LION-2ndVerse");
        setAltNames("エムシーリオンセカンドヴァース Emu Shii Rion Sekando Vaasu");
        
        setName("en", "MC LION - 2nd Verse");
        
        setName("en_fan", "MC.LION - 2nd Verse");
        
		setName("zh_simplified", "MC.LION-2ndVerse");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
