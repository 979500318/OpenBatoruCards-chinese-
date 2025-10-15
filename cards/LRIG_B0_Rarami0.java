package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_Rarami0 extends Card {
    
    public LRIG_B0_Rarami0()
    {
        setImageSets("PR-390");
        
        setOriginalName("ららみ！０");
        setAltNames("ララミゼロ Rarami Zero");
        
        setName("en", "Rarami! 0");
        
		setName("zh_simplified", "啦啦美！0");
        setLRIGType(CardLRIGType.AYA);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setCoins(+4);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
