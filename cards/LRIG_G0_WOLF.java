package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G0_WOLF extends Card {
    
    public LRIG_G0_WOLF()
    {
        setImageSets("SPDi03-06");
        
        setOriginalName("ＷＯＬＦ");
        setAltNames("ウルフ Urufu");
        
        setName("en", "WOLF");
        
		setName("zh_simplified", "WOLF");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.WOLF);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
