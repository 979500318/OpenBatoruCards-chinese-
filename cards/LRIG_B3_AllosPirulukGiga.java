package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.Cost;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B3_AllosPirulukGiga extends Card {
    
    public LRIG_B3_AllosPirulukGiga()
    {
        setImageSets("WX17-016", "WXK01-015");
        
        setOriginalName("アロス・ピルルク　Ｇ");
        setAltNames("アロスピルルクギガ Arosu Piruruku Giga");
        
        setName("en", "Allos Piruluk Giga");
        
		setName("zh_simplified", "阿洛斯·皮璐璐可 G");
        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(8);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
