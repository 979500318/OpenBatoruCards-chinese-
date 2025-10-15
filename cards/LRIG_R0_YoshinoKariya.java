package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R0_YoshinoKariya extends Card {
    
    public LRIG_R0_YoshinoKariya()
    {
        setImageSets("SPDi16-01");
        
        setOriginalName("雁矢よしの");
        setAltNames("カリヤヨシノ Kariya Yoshino");
        
        setName("en", "Yoshino Kariya");
        
		setName("zh_simplified", "雁矢吉乃");
        setLRIGType(CardLRIGType.RIL);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCoins(+1);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
