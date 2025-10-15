package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_G2_BangStandingAhead extends Card {
    
    public LRIG_G2_BangStandingAhead()
    {
        setImageSets("WXDi-P02-022");
        
        setOriginalName("マエニタツ　バン");
        setAltNames("マエニタツバン Mae ni Tatsu Ban");
        
        setName("en", "Bang, Front and Center");
        
        setName("en_fan", "Bang, Standing Ahead");
        
		setName("zh_simplified", "领先站 梆");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.BANG);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
