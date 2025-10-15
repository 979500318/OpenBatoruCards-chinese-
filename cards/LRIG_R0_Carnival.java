package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_R0_Carnival extends Card {
    
    public LRIG_R0_Carnival()
    {
        setImageSets("PR-412", Mask.IGNORE+"WX17_JR-001", Mask.IGNORE+"WX17-CL05",Mask.IGNORE+"WX21-GC04");
        
        setOriginalName("カーニバル");
        setAltNames("Kaanibaru");
        
        setName("en", "Carnival");
        
		setName("zh_simplified", "嘉年华");
        setLRIGType(CardLRIGType.CARNIVAL);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCoins(+3);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
