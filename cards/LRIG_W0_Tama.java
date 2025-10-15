package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W0_Tama extends Card {
    
    public LRIG_W0_Tama()
    {
        setImageSets(Mask.IGNORE+"PR-005", "WD13-007", Mask.IGNORE+"WX10-TR01", "WX18-BB01", Mask.IGNORE+"WX21-GC01", "SP09-001", "SPDi03-13","SPDi35-01","SPDi36-01");
        
        setOriginalName("タマ");
        setAltNames("Tama");
        
        setName("en", "Tama");
        
		setName("zh_simplified", "小玉");
        setLRIGType(CardLRIGType.TAMA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
