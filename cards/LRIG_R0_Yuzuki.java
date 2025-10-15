package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_R0_Yuzuki extends Card {
    
    public LRIG_R0_Yuzuki()
    {
        setImageSets("PR-069", Mask.IGNORE+"WX11-TR01", Mask.IGNORE+"WX19-BM03", "SPDi03-14","SPDi34-13");
        
        setOriginalName("遊月");
        setAltNames("ユヅキ Yuzuki");
        
        setName("en", "Yuzuki");
        
		setName("zh_simplified", "游月");
        setLRIGType(CardLRIGType.YUZUKI);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
