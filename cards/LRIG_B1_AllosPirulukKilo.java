package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B1_AllosPirulukKilo extends Card {
    
    public LRIG_B1_AllosPirulukKilo()
    {
        setImageSets("WX17-018", "WXDi-P11-015");
        
        setOriginalName("アロス・ピルルク　Ｋ");
        setAltNames("アロスピルルクキロ Arosu Piruruku Kiro Kilo");
        
        setName("en", "Allos Piruluk K");
        
        setName("en_fan", "Allos Piruluk K");
        
		setName("zh_simplified", "阿洛斯·皮璐璐可 K");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.KEY_CLASSIC, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
