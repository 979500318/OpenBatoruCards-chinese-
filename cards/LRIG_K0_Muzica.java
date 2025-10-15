package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K0_Muzica extends Card {
    
    public LRIG_K0_Muzica()
    {
        setImageSets("SPDi03-10");
        
        setOriginalName("ムジカ");
        setAltNames("Mujika");
        
        setName("en", "Muzica");
        
		setName("zh_simplified", "穆希卡");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MUZICA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
