package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W0_TayoriKingyobachi extends Card {
    
    public LRIG_W0_TayoriKingyobachi()
    {
        setImageSets("SPDi16-03");
        
        setOriginalName("金魚鉢たより");
        setAltNames("キンギョバチタヨリ Kingyobachi Tayori");
        
        setName("en", "Tayori Kingyobachi");
        
		setName("zh_simplified", "金鱼钵信");
        setLRIGType(CardLRIGType.TAMA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
