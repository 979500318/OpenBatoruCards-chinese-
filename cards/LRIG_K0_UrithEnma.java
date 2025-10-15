package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K0_UrithEnma extends Card {
    
    public LRIG_K0_UrithEnma()
    {
        setImageSets(Mask.IGNORE+"WD05-005", "WXDi-P06-020", "WX24-D5-01",
                Mask.IGNORE+"SP03-005",Mask.IGNORE+"SP04-005","SP05-005","SP13-006A","SP13-006B","SP34-007","SP35-016",
                "PR-059",Mask.IGNORE+"PR-127",Mask.IGNORE+"PR-162",Mask.IGNORE+"PR-244", "SPDi01-36","SPDi14-04","SPDi21-05","SPDi23-12"
        );
        
        setOriginalName("閻魔　ウリス");
        setAltNames("エンマウリス Enma Urisu Ulith");
        
        setName("en", "Urith, Enma");
        
        setName("en_fan", "Urith, Enma");
        
		setName("zh_simplified", "阎魔 乌莉丝");
        setLRIGType(CardLRIGType.URITH);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
