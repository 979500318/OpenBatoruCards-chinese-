package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G1_HitoeFirstFormula extends Card {
    
    public LRIG_G1_HitoeFirstFormula()
    {
        setImageSets("WX24-P2-024");
        
        setOriginalName("壱式　一衣");
        setAltNames("イチシキヒトエ Ichishiki Hitoe");
        
        setName("en", "Hitoe, First Formula");
        
		setName("zh_simplified", "壹式 一衣");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HITOE);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
