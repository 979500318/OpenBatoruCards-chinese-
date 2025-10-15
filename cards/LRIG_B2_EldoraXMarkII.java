package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_B2_EldoraXMarkII extends Card {
    
    public LRIG_B2_EldoraXMarkII()
    {
        setImageSets("WX02-011", "SP33-018", "WXDi-P08-015", "WX24-P3-021");
        
        setOriginalName("エルドラ×マークⅡ");
        setAltNames("エルドラマークツー Erudora Maaku Tsuu EldoraxMark II Eldora x Mark II");
        
        setName("en", "Eldora × Mark Ⅱ");
        
        setName("en_fan", "Eldora×Mark II");
        
		setName("zh_simplified", "艾尔德拉×Ⅱ式");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ELDORA);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
