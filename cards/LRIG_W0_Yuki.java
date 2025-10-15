package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W0_Yuki extends Card {
    
    public LRIG_W0_Yuki()
    {
        setImageSets("PR-K040", Mask.IGNORE+"WX19-BM01");
        
        setOriginalName("ユキ");
        setAltNames("Yuki");
        
        setName("en", "Yuki");
        
		setName("zh_simplified", "雪");
        setLRIGType(CardLRIGType.IONA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
