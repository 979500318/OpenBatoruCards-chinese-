package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_R0_Ril extends Card {
    
    public LRIG_R0_Ril()
    {
        setImageSets("PR-350", "WX16_JR-002", Mask.IGNORE+"WX17-CL02",Mask.IGNORE+"WX17-CL12","WX18-BB15", "SPDi03-17","SPDi34-16");
        
        setOriginalName("リル");
        setAltNames("Riru");
        
        setName("en", "Ril");
        
		setName("zh_simplified", "莉露");
        setLRIGType(CardLRIGType.RIL);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCoins(+1);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
