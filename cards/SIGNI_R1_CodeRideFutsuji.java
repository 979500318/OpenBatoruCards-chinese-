package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R1_CodeRideFutsuji extends Card {
    
    public SIGNI_R1_CodeRideFutsuji()
    {
        setImageSets("WDK01-016");
        
        setOriginalName("コードライド　フツージ");
        setAltNames("コードライドフツージ Koodo Raido Futsuuji");
        
        setName("en", "Code Ride Futsuji");
        
		setName("zh_simplified", "骑乘代号 普通自动车");
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
