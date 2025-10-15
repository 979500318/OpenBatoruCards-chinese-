package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B0_TheNameIsCasual extends Card {
    
    public LRIG_B0_TheNameIsCasual()
    {
        setImageSets("PR-Di023");
        
        setOriginalName("その名は　カジュアる");
        setAltNames("ソノナハカジュアル Sono Na wa Kajuaru");
        
        setName("en", "The Name is Casual");
        
		setName("zh_simplified", "那位的名是  卡苏艾露");
        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
