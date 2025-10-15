package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K1_IonaNeptoMaiden extends Card {
    
    public LRIG_K1_IonaNeptoMaiden()
    {
        setImageSets(Mask.IGNORE+"WX04-024", "WXDi-P08-020", "WX24-P2-028");
        
        setOriginalName("ネプト/メイデン　イオナ");
        setAltNames("ネプトメイデンイオナ Neputo Meiden Iona");
        
        setName("en", "Iona, Nepto/Maiden");
        
        setName("en_fan", "Iona, Nepto/Maiden");
        
		setName("zh_simplified", "海王/少女 伊绪奈");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
