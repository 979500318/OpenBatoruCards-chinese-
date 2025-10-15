package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G0_HitoeZeroFormula extends Card {
    
    public LRIG_G0_HitoeZeroFormula()
    {
        setImageSets("WX24-P2-023", "SPDi42-7P");
        
        setOriginalName("零式　一衣");
        setAltNames("ゼロシキヒトエ Zeroshiki Hitoe");
        
        setName("en", "Hitoe, Zero Formula");
        
		setName("zh_simplified", "零式 一衣");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HITOE);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
