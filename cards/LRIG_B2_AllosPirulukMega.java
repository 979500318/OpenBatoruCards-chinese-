package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_B2_AllosPirulukMega extends Card {
    
    public LRIG_B2_AllosPirulukMega()
    {
        setImageSets("WX17-017", "WXK01-016", "WXDi-P11-016");
        
        setOriginalName("アロス・ピルルク　Ｍ");
        setAltNames("アロスピルルクメガ Arosu Piruruku Mega");
        
        setName("en", "Allos Piruluk M");
        
        setName("en_fan", "Allos Piruluk M");
        
		setName("zh_simplified", "阿洛斯·皮璐璐可 M");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.KEY_CLASSIC, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
