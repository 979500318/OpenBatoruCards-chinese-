package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_K2_NanashiPartTwo extends Card {
    
    public LRIG_K2_NanashiPartTwo()
    {
        setImageSets("WX15-024", "WXK08-020", "WXDi-P09-023");
        
        setOriginalName("ナナシ　其ノ爾");
        setAltNames("ナナシソノニ Nanashi Sono Ni");
        
        setName("en", "Nanashi, Part Two");
        
        setName("en_fan", "Nanashi, Part Two");
        
		setName("zh_simplified", "无名 其之贰");
        setLRIGType(CardLRIGType.NANASHI);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
