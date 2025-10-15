package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_B2_NeruMikamoCruisinForABruisin extends Card {
    
    public LRIG_B2_NeruMikamoCruisinForABruisin()
    {
        setImageSets("WXDi-CP02-016");
        
        setOriginalName("美甘ネル[あぁ？ふざけんな！]");
        setAltNames("ミカモネルアァフザケンナ Mikamo Neru Aa Fuzakenna");
        
        setName("en", "Mikamo Neru [Cruisin' for a Bruisin'!]");
        
        
        setName("en_fan", "Neru Mikamo [Cruisin' for a Bruisin'!]");
        
		setName("zh_simplified", "美甘妮露[啊~？把我当傻子是吧！]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NERU);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

