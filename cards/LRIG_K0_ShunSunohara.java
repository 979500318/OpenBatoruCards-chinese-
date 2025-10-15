package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K0_ShunSunohara extends Card {
    
    public LRIG_K0_ShunSunohara()
    {
        setImageSets("WX25-CP1-021");
        
        setOriginalName("春原シュン");
        setAltNames("スノハラシュン Sunohara Shun");
        
        setName("en", "Sunohara Shun");
        
        setName("en_fan", "Shun Sunohara");
        
		setName("zh_simplified", "春原瞬");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SHUN);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
