package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K0_Guzuko extends Card {
    
    public LRIG_K0_Guzuko()
    {
        setImageSets("PR-413", Mask.IGNORE+"WX17-CL06", "WX18_JR-003", "SPDi34-19");
        
        setOriginalName("グズ子");
        setAltNames("グズコ Guzuko");
        
        setName("en", "Guzuko");
        
		setName("zh_simplified", "迟钝子");
        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setCoins(+3);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
