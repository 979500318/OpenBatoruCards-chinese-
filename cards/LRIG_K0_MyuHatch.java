package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K0_MyuHatch extends Card {
    
    public LRIG_K0_MyuHatch()
    {
        setImageSets("WD11-005", "SP12-015","SP20-011","SP34-015","SP35-025", "PR-199",Mask.IGNORE+"PR-230",
                     "WDK13-005", "SPK03-17","SPK04-17","SPK16-3C","SPK18-04","SPK19-03",
                     "WXDi-P11-023","WX25-P2-027", "SPDi01-66"
        );
        
        setOriginalName("ミュウ＝ハッチ");
        setAltNames("ミュウハッチ Myuu Hatchi");
        
        setName("en", "Myu=Hatch");
        
        setName("en_fan", "Myu-Hatch");
        
		setName("zh_simplified", "缪＝孵化");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MYU);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
