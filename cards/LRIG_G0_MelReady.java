package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G0_MelReady extends Card {
    
    public LRIG_G0_MelReady()
    {
        setImageSets("WD18-005", "SP20-004","SP21-004","SP24-005","SP29-006","SP30-006","SP34-006","SP35-019", "PR-345",
                     "WDK12-005", "SPK03-16","SPK04-16","SPK14-04","SPK16-3B","SPK18-03","SPK19-02",
                     "WXDi-P07-027", "SPDi01-40","SPDi23-14"
        );
        
        setOriginalName("メル＝レディ");
        setAltNames("メルレディ Meru Redi");
        
        setName("en", "Mel - Ready");
        
        setName("en_fan", "Mel-Ready");
        
		setName("zh_simplified", "梅露=佳人");
        setLRIGType(CardLRIGType.MEL);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCoins(+3);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
