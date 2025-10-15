package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_R0_LalaruN extends Card {
    
    public LRIG_R0_LalaruN()
    {
        setImageSets("WX25-P2-015", Mask.IGNORE+"PR-227");
        
        setOriginalName("ララ・ルー\"N\"");
        setAltNames("ララルーニュートラル Rararuu Nyuutoraru");
        
        setName("en", "Lalaru \"Neutral\"");
        
		setName("zh_simplified", "啦啦·噜“N”");
        setLRIGType(CardLRIGType.LALARU);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
