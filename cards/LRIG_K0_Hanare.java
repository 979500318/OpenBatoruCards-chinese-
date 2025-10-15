package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K0_Hanare extends Card {
    
    public LRIG_K0_Hanare()
    {
        setImageSets("PR-303", Mask.IGNORE+"WX14-TR02", Mask.IGNORE+"WX19-BM10");
        
        setOriginalName("ハナレ");
        setAltNames("Hanare");
        
        setName("en", "Hanare");
        
		setName("zh_simplified", "离");
        setLRIGType(CardLRIGType.HANARE);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
