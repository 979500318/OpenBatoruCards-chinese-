package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B0_Piruluk extends Card {
    
    public LRIG_B0_Piruluk()
    {
        setImageSets(Mask.IGNORE+"PR-007","PR-411", Mask.IGNORE+"WX10-TR03","WX16_JR-004", "WX17-CL11","WX18-BB05",Mask.IGNORE+"WX21-GC05", "SP09-003",
                     "SPDi03-15","SPDi34-12","SPDi35-02","SPDi36-03");
        
        setOriginalName("ピルルク");
        setAltNames("ピルルク Piruruku");
        
        setName("en", "Piruluk");
        
		setName("zh_simplified", "皮璐璐可");
        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
