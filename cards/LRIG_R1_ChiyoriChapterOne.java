package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R1_ChiyoriChapterOne extends Card {
    
    public LRIG_R1_ChiyoriChapterOne()
    {
        setImageSets("WX24-P3-016");
        
        setOriginalName("ちより　第一章");
        setAltNames("チヨリダイイッショウ Chiyori Daiisshou");
        
        setName("en", "Chiyori, Chapter One");
        
		setName("zh_simplified", "千依 第一章");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.CHIYORI);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
