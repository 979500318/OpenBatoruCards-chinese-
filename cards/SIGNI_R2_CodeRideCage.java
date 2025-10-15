package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R2_CodeRideCage extends Card {
    
    public SIGNI_R2_CodeRideCage()
    {
        setImageSets("WXK01-075");
        
        setOriginalName("コードライド　ケイジ");
        setAltNames("コードライドケイジ Koodo Raido Keiji");
        
        setName("en", "Code Ride Cage");
        
		setName("zh_simplified", "骑乘代号 轻型车");
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(2);
        setPower(7000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
