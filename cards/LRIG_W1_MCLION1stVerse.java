package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W1_MCLION1stVerse extends Card {
    
    public LRIG_W1_MCLION1stVerse()
    {
        setImageSets("WXDi-D04-002");
        
        setOriginalName("MC.LION-1stVerse");
        setAltNames("エムシーリオンファーストヴァース Emu Shii Rion Faasuto Vaasu");
        
        setName("en", "MC LION - 1st Verse");
        
        setName("en_fan", "MC.LION - 1st Verse");
        
		setName("zh_simplified", "MC.LION-1stVerse");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
