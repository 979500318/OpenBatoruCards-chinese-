package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_B2_LiberatorEldoraXMarkII extends Card {
    
    public LRIG_B2_LiberatorEldoraXMarkII()
    {
        setImageSets("WXDi-P15-017");
        
        setOriginalName("解放者エルドラ×マークⅠⅠ");
        setAltNames("カイホウシャエルドラマークツー Kaihousha Erudora Maaku Tsuu Eldora x Mark 2");
        
        setName("en", "Liberator Eldora × Mark Ⅱ");
        
        
        setName("en_fan", "Liberator Eldora×Mark II");
        
		setName("zh_simplified", "解放者艾尔德拉×Ⅱ式");
        setLRIGType(CardLRIGType.ELDORA);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
