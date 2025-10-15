package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B1_PikopikoAya extends Card {
    
    public LRIG_B1_PikopikoAya()
    {
        setImageSets("WXDi-P07-020");
        
        setOriginalName("ぴこぴこあーや！Ⅰ");
        setAltNames("ピコピコアーヤワン Pikopiko Aaya Wan");
        
        setName("en", "Beep Boop Aya! I");
        
        setName("en_fan", "Pikopiko Aya! I");
        
		setName("zh_simplified", "鸣笛亚弥！I");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AYA);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
