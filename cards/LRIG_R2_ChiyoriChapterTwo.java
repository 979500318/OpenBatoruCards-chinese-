package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_R2_ChiyoriChapterTwo extends Card {
    
    public LRIG_R2_ChiyoriChapterTwo()
    {
        setImageSets("WX24-P3-017");
        
        setOriginalName("ちより　第二章");
        setAltNames("チヨリダイニショウ Chiyori Dainishou");
        
        setName("en", "Chiyori, Chapter Two");
        
		setName("zh_simplified", "千依 第二章");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.CHIYORI);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
