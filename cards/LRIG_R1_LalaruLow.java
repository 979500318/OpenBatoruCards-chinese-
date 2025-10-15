package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_R1_LalaruLow extends Card {
    
    public LRIG_R1_LalaruLow()
    {
        setImageSets("WX25-P2-016");
        
        setOriginalName("ララ・ルー\"Low\"");
        setAltNames("ララルーロー Rararuu Roo");
        
        setName("en", "Lalaru \"Low\"");
        
		setName("zh_simplified", "啦啦·噜“Low”");
        setLRIGType(CardLRIGType.LALARU);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
