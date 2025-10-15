package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_R0_Hanayo extends Card {
    
    public LRIG_R0_Hanayo()
    {
        setImageSets(Mask.IGNORE+"PR-006", "SP09-002", Mask.IGNORE+"WX10-TR02", "WX18-BB03",Mask.IGNORE+"WX21-GC03", "SPDi36-02");
        
        setOriginalName("花代");
        setAltNames("ハナヨ Hanayo");
        
        setName("en", "Hanayo");
        
		setName("zh_simplified", "花代");
        setLRIGType(CardLRIGType.HANAYO);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
