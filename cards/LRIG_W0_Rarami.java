package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W0_Rarami extends Card {
    
    public LRIG_W0_Rarami()
    {
        setImageSets("PR-278");
        
        setOriginalName("ららみ");
        setAltNames("ララミ Rarami");
        
        setName("en", "Rarami");
        
		setName("zh_simplified", "啦啦美");
        setLRIGType(CardLRIGType.TAWIL);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
