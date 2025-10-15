package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K0_Umr extends Card {
    
    public LRIG_K0_Umr()
    {
        setImageSets("PR-171", Mask.IGNORE+"WX12-TR05", "WX18_JR-001", "WX18-BB23", "SPDi34-22");
        
        setOriginalName("ウムル");
        setAltNames("Umuru");
        
        setName("en", "Umr");
        
		setName("zh_simplified", "塔维尔");
        setLRIGType(CardLRIGType.UMR);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
