package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R0_Carnival0 extends Card {
    
    public LRIG_R0_Carnival0()
    {
        setImageSets("WD21-005", "SP20-008","SP29-003","SP30-003","SP34-011", "PR-398", "WXDi-P09-011", "SPDi01-48","SPDi21-06");
        
        setOriginalName("カーニバル　―０―");
        setAltNames("カーニバルゼロ Kaanibaru Zero");
        
        setName("en", "Carnival -0-");
        
        setName("en_fan", "Carnival -0-");
        
		setName("zh_simplified", "嘉年华 -0-");
        setLRIGType(CardLRIGType.CARNIVAL);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCoins(+3);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
