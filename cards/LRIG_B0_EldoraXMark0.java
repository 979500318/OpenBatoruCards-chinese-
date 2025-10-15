package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B0_EldoraXMark0 extends Card {
    
    public LRIG_B0_EldoraXMark0()
    {
        setImageSets(Mask.IGNORE+"PR-041", Mask.IGNORE+"SP08-003","SP16-001", Mask.IGNORE+"PR-128","PR-202", "WXDi-P08-013", "WX24-P3-019", "SPDi01-42","SPDi23-15");
        
        setOriginalName("エルドラ×マーク0");
        setAltNames("エルドラマークゼロ Erudora Maaku Zero EldoraxMark 0 Eldora x Mark 0");
        
        setName("en", "Eldora × Mark ０");
        
        setName("en_fan", "Eldora×Mark 0");
        
		setName("zh_simplified", "艾尔德拉×０式");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ELDORA);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
