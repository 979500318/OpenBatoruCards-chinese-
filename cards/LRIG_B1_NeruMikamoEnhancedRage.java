package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B1_NeruMikamoEnhancedRage extends Card {
    
    public LRIG_B1_NeruMikamoEnhancedRage()
    {
        setImageSets("WXDi-CP02-015");
        
        setOriginalName("美甘ネル[激昂]");
        setAltNames("ミカモネルゲキコウ Mikamo Neru Gekikou");
        
        setName("en", "Mikamo Neru [Enhanced Rage]");
        
        
        setName("en_fan", "Neru Mikamo [Enhanced Rage]");
        
		setName("zh_simplified", "美甘妮露[激昂]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NERU);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

