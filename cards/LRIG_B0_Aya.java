package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B0_Aya extends Card {
    
    public LRIG_B0_Aya()
    {
        setImageSets("PR-351", "WX16_JR-003", Mask.IGNORE+"WX17-CL07","WX18-BB17", "SPDi03-19");
        
        setOriginalName("あーや");
        setAltNames("アーヤ Aaya");
        
        setName("en", "Aya");
        
		setName("zh_simplified", "亚弥");
        setLRIGType(CardLRIGType.AYA);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setCoins(+4);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
