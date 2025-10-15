package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K0_Urith extends Card {
    
    public LRIG_K0_Urith()
    {
        setImageSets("PR-066","SPK22-03", "WD14-006", Mask.IGNORE+"WX10-TR05", "WX18-BB09", "SP09-005", "SPDi03-16","SPDi34-15","SPDi35-03","SPDi36-05");
        
        setOriginalName("ウリス");
        setAltNames("Urisu Ulith");
        
        setName("en", "Urith");
        
		setName("zh_simplified", "乌莉丝");
        setLRIGType(CardLRIGType.URITH);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
