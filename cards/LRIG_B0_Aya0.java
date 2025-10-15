package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B0_Aya0 extends Card {
    
    public LRIG_B0_Aya0()
    {
        setImageSets("PR-340", Mask.IGNORE+"WX21-GC06", "SP20-003","SP21-003","SP29-004","SP30-004","SP34-005","SP35-018", "PR-344", "WXDi-P07-019", "SPDi01-39","SPDi14-07");
        
        setOriginalName("あーや！０");
        setAltNames("アーヤゼロ Aaya Zero");
        
        setName("en", "Aya! 0");
        
        setName("en_fan", "Aya! 0");
        
		setName("zh_simplified", "亚弥！ 0");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AYA);
        setColor(CardColor.BLUE);
        setCoins(+4);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
