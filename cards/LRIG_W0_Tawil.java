package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W0_Tawil extends Card {
    
    public LRIG_W0_Tawil()
    {
        setImageSets("PR-170", Mask.IGNORE+"WX12-TR03", "WX18_JR-002", "WX18-BB11", "SPDi34-23");
        
        setOriginalName("タウィル");
        setAltNames("Tawil");
        
        setName("en", "Tawil");
        
		setName("zh_simplified", "乌姆尔");
        setLRIGType(CardLRIGType.TAWIL);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
