package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W0_Remember extends Card {
    
    public LRIG_W0_Remember()
    {
        setImageSets("PR-298", Mask.IGNORE+"WX12-TR02");
        
        setOriginalName("リメンバ");
        setAltNames("Rimenba");
        
        setName("en", "Remember");
        
		setName("zh_simplified", "忆");
        setLRIGType(CardLRIGType.REMEMBER);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
