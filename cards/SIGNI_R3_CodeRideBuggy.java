package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R3_CodeRideBuggy extends Card {
    
    public SIGNI_R3_CodeRideBuggy()
    {
        setImageSets("WXK01-073");
        
        setOriginalName("コードライド　バギー");
        setAltNames("コードライドバギー Koodo Raido Bagii");
        
        setName("en", "Code Ride Buggy");
        
		setName("zh_simplified", "骑乘代号 全地形车");
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
