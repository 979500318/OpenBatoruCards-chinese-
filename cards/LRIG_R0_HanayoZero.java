package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_R0_HanayoZero extends Card {
    
    public LRIG_R0_HanayoZero()
    {
        setImageSets(Mask.IGNORE+"WD02-005", "WXDi-D08-005", "WX24-D2-01",
                Mask.IGNORE+"SP04-002","SP05-002","SP13-003A","SP13-003B","SP18-003","SP35-013",
                Mask.IGNORE+"PR-010",Mask.IGNORE+"PR-014",Mask.IGNORE+"PR-124",Mask.IGNORE+"PR-159", "SPDi01-32","SPDi23-16"
        );
        
        setOriginalName("花代・零");
        setAltNames("ハナヨゼロ");
        
        setName("en", "Hanayo, Zero");
        
        setName("en_fan", "Hanayo-Zero");
        
		setName("zh_simplified", "花代·零");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
