package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B0_CodePiruluk extends Card {
    
    public LRIG_B0_CodePiruluk()
    {
        setImageSets(Mask.IGNORE+"WD03-005", "WD16-005", "WXDi-P06-017", "WX24-D3-01","WX25-P2-031",
                Mask.IGNORE+"SP04-003","SP05-003","SP13-004A","SP13-004B","SP14-001","SP18-004",
                "PR-011",Mask.IGNORE+"PR-015",Mask.IGNORE+"PR-125",Mask.IGNORE+"PR-160", Mask.IGNORE+"P15-021",
                "SPK14-03", "SPDi01-35","SPDi14-03","SPDi15-04","SPDi21-03","SPDi23-06","SPDi24-03"
        );
        
        setOriginalName("コード・ピルルク");
        setAltNames("コードピルルク Koodo Piruruku");
        
        setName("en", "Code Piruluk");
        
        setName("en_fan", "Code Piruluk");
        
		setName("zh_simplified", "代号·皮璐璐可");
        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
