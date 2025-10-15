package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K0_IonaZeroMaiden extends Card {
    
    public LRIG_K0_IonaZeroMaiden()
    {
        setImageSets("WD07-005", "WXDi-P08-019", "WX24-P2-027", Mask.IGNORE+"SP08-005", "PR-062",Mask.IGNORE+"PR-130", "SPDi01-44");
        
        setOriginalName("ゼロ/メイデン　イオナ");
        setAltNames("ゼロメイデンイオナ Zero Meiden Iona");
        
        setName("en", "Iona, Zero/Maiden");
        
        setName("en_fan", "Iona, Zero/Maiden");
        
		setName("zh_simplified", "朔月/少女 伊绪奈");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
