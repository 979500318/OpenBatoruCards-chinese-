package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_G2_HitoeSecondFormula extends Card {
    
    public LRIG_G2_HitoeSecondFormula()
    {
        setImageSets("WX24-P2-025");
        
        setOriginalName("弐式　一衣");
        setAltNames("ニシキヒトエ Nishiki Hitoe");
        
        setName("en", "Hitoe, Second Formula");
        
		setName("zh_simplified", "贰式 一衣");
        setLRIGType(CardLRIGType.HITOE);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
