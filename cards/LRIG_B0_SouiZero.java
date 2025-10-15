package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B0_SouiZero extends Card {
    
    public LRIG_B0_SouiZero()
    {
        setImageSets("WX25-P2-019", Mask.IGNORE+"PR-228");
        
        setOriginalName("ソウイ＝ゼロ");
        setAltNames("ソウイゼロ Soui Zero");
        
        setName("en", "Soui-Zero");
        
		setName("zh_simplified", "索薇=零");
        setLRIGType(CardLRIGType.SOUI);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
