package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_K2_IonaUranusMaiden extends Card {
    
    public LRIG_K2_IonaUranusMaiden()
    {
        setImageSets("WX04-023", "WXDi-P08-021", "WX24-P2-029");
        
        setOriginalName("ウラヌス/メイデン　イオナ");
        setAltNames("ウラヌスメイデンイオナ Uranusu Meiden Iona");
        
        setName("en", "Iona, Uranus/Maiden");
        
        setName("en_fan", "Iona, Uranus/Maiden");
        
		setName("zh_simplified", "天王/少女 伊绪奈");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
