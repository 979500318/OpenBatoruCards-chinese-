package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_LiberatorEldoraXMark0 extends Card {
    
    public LRIG_B0_LiberatorEldoraXMark0()
    {
        setImageSets("WXDi-P15-015");
        
        setOriginalName("解放者エルドラ×マーク0");
        setAltNames("カイホウシャエルドラマークゼロ Kaihousha Erudora Maaku Zero");
        
        setName("en", "Liberator Eldora × Mark 0");
        
        
        setName("en_fan", "Liberator Eldora×Mark 0");
        
		setName("zh_simplified", "解放者艾尔德拉×０式");
        setLRIGType(CardLRIGType.ELDORA);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
