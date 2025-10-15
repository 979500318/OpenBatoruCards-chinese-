package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B1_LiberatorEldoraXMarkI extends Card {
    
    public LRIG_B1_LiberatorEldoraXMarkI()
    {
        setImageSets("WXDi-P15-016");
        
        setOriginalName("解放者エルドラ×マークⅠ");
        setAltNames("カイホウシャエルドラマークワン Kaihousha Erudora Maaku Wan Eldora x Mark 1");
        
        setName("en", "Liberator Eldora × Mark Ⅰ");
        
        
        setName("en_fan", "Liberator Eldora×Mark I");
        
		setName("zh_simplified", "解放者艾尔德拉×Ⅰ式");
        setLRIGType(CardLRIGType.ELDORA);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
