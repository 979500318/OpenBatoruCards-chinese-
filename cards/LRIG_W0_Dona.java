package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W0_Dona extends Card {
    
    public LRIG_W0_Dona()
    {
        setImageSets("PR-410", "WX16_JR-001", "WX17-CL04","WX18-BB13", Mask.IGNORE+"WX21-GC02", "SPDi03-18");
        
        setOriginalName("ドーナ");
        setAltNames("Doona");
        
        setName("en", "Dona");
        
		setName("zh_simplified", "多娜");
        setLRIGType(CardLRIGType.DONA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        setCoins(+3);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
