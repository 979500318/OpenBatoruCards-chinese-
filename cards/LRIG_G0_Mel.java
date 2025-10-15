package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_G0_Mel extends Card {
    
    public LRIG_G0_Mel()
    {
        setImageSets("PR-352","SPK22-05", "WX16_JR-005", Mask.IGNORE+"WX17-CL08","WX18-BB19");
        
        setOriginalName("メル");
        setAltNames("Meru");
        
        setName("en", "Mel");
        
		setName("zh_simplified", "梅露");
        setLRIGType(CardLRIGType.MEL);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCoins(+3);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
