package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_NeruMikamo extends Card {
    
    public LRIG_B0_NeruMikamo()
    {
        setImageSets("WXDi-CP02-014", "SPDi31-02","SPDi32-02");
        
        setOriginalName("美甘ネル");
        setAltNames("ミカモネル Mikamo Neru");
        
        setName("en", "Mikamo Neru");
        
        
        setName("en_fan", "Neru Mikamo");
        
		setName("zh_simplified", "美甘妮露");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NERU);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

