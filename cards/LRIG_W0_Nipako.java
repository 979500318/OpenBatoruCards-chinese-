package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_Nipako extends Card {
    
    public LRIG_W0_Nipako()
    {
        setImageSets("PR-425");
        
        setOriginalName("ニパ子");
        setAltNames("ニパコ Nipako");
        
        setName("en", "Nipako");
        
		setName("zh_simplified", "尼帕子");
        setLRIGType(CardLRIGType.TAMA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
