package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_G0_Midoriko extends Card {
    
    public LRIG_G0_Midoriko()
    {
        setImageSets(Mask.IGNORE+"PR-008", "SP09-004", Mask.IGNORE+"WX10-TR04", "WX18-BB07", "SPDi36-04");
        
        setOriginalName("緑姫");
        setAltNames("ミドリコ Midoriko");
        
        setName("en", "Midoriko");
        
		setName("zh_simplified", "绿子");
        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
