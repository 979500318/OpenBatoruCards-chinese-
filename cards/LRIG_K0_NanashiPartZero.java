package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K0_NanashiPartZero extends Card {
    
    public LRIG_K0_NanashiPartZero()
    {
        setImageSets("WD19-005", "SP20-006","SP21-005","SP29-008","SP30-008","SP34-008","SP35-021", "PR-341","PR-347",
                     "WDK15-005", "SPK03-19","SPK04-19","SPK19-05", "WXDi-P07-033", "SPDi01-41");
        
        setOriginalName("ナナシ　其ノ無");
        setAltNames("ナナシソノナシ Nanashi Sono Nashi");
        
        setName("en", "Nanashi, Part Zero");
        
        setName("en_fan", "Nanashi, Part Zero");
        
		setName("zh_simplified", "无名 其之无");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NANASHI);
        setColor(CardColor.BLACK);
        setCoins(+2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
