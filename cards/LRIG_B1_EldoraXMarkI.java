package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B1_EldoraXMarkI extends Card {
    
    public LRIG_B1_EldoraXMarkI()
    {
        setImageSets(Mask.IGNORE+"WX02-012", "SP33-019", "WXDi-P08-014", "WX24-P3-020");
        
        setOriginalName("エルドラ×マークⅠ");
        setAltNames("エルドラマークワン Erudora Maaku Wan EldoraxMark I Eldora x Mark I");
        
        setName("en", "Eldora × Mark Ⅰ");
        
        setName("en_fan", "Eldora×Mark I");
        
		setName("zh_simplified", "艾尔德拉×Ⅰ式");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ELDORA);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
