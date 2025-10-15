package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_R0_YuzukiZero extends Card {
    
    public LRIG_R0_YuzukiZero()
    {
        setImageSets("WD15-005", Mask.IGNORE+"WX18-091", "WXDi-P06-014", "WX24-P2-015", Mask.IGNORE+"SP02-005",Mask.IGNORE+"SP08-001","SP16-002","SP34-003",
                     Mask.IGNORE+"PR-042","PR-061",Mask.IGNORE+"PR-129","PR-163", "SPDi01-34","SPDi14-02","SPDi23-17");
        
        setOriginalName("遊月・零");
        setAltNames("ユヅキゼロ Yuzuki Zero");
        
        setName("en", "Yuzuki Zero");
        
        setName("en_fan", "Yuzuki-Zero");
        
		setName("zh_simplified", "游月·零");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUZUKI);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
