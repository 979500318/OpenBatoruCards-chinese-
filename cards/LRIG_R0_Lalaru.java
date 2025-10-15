package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_R0_Lalaru extends Card {
    
    public LRIG_R0_Lalaru()
    {
        setImageSets(Mask.IGNORE+"WX13-TR02");
        
        setOriginalName("ララ・ルー");
        setAltNames("ララルー Rararuu");
        
        setName("en", "Lalaru");
        
		setName("zh_simplified", "LALARU");
        setLRIGType(CardLRIGType.LALARU);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
