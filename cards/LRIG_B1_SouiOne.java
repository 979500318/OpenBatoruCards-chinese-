package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B1_SouiOne extends Card {
    
    public LRIG_B1_SouiOne()
    {
        setImageSets("WX25-P2-020");
        
        setOriginalName("ソウイ＝ワン");
        setAltNames("ソウイワン Soui Wan");
        
        setName("en", "Soui-One");
        
		setName("zh_simplified", "索薇=壹");
        setLRIGType(CardLRIGType.SOUI);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
