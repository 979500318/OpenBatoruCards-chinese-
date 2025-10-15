package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_B2_AllosPirulukLitre extends Card {
    
    public LRIG_B2_AllosPirulukLitre()
    {
        setImageSets("WXDi-P14-015");
        
        setOriginalName("アロス・ピルルク　ℓ");
        setAltNames("アロスピルルクリットル Arosu Piruruku Rittoru l");
        
        setName("en", "Allos Piruluk ℓ");
        
        setName("en_fan", "Allos Piruluk Litre");
        
		setName("zh_simplified", "阿洛斯·皮璐璐可　l");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
