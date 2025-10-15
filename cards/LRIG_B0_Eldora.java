package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B0_Eldora extends Card {
    
    public LRIG_B0_Eldora()
    {
        setImageSets("PR-068", Mask.IGNORE+"WX11-TR02", Mask.IGNORE+"WX19-BM04", "PR-K039", "SPDi34-17");
        
        setOriginalName("エルドラ");
        setAltNames("Erudora");
        
        setName("en", "Eldora");
        
		setName("zh_simplified", "艾尔德拉");
        setLRIGType(CardLRIGType.ELDORA);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
