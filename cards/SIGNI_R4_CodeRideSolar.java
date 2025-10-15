package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R4_CodeRideSolar extends Card {
    
    public SIGNI_R4_CodeRideSolar()
    {
        setImageSets("WXK01-071");
        
        setOriginalName("コードライド　ソーラー");
        setAltNames("コードライドソーラー Koodo Raido Sooraa");
        
        setName("en", "Code Ride Solar");
        
		setName("zh_simplified", "骑乘代号 太阳能汽车");
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(4);
        setPower(15000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
