package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G0_ARuFasMama extends Card {
    
    public LRIG_G0_ARuFasMama()
    {
        setImageSets("WXDi-P09-019-M01", "SPDi17-01");
        
        setOriginalName("ARuFa母");
        setAltNames("アルファハハ Arufa Haha");
        
        setName("en", "ARuFa's Mama");
        
        
        setName("en_fan", "ARuFa's Mama");
        
		setName("zh_simplified", "ARuFa母");
        setLRIGType(CardLRIGType.MAMA);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCoins(+4);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
