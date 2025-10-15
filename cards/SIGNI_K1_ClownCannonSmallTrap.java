package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_ClownCannonSmallTrap extends Card {
    
    public SIGNI_K1_ClownCannonSmallTrap()
    {
        setImageSets("WDK04-016");
        
        setOriginalName("小罠　クラウン・キャノン");
        setAltNames("ショウビンクラウンキャノン Shoubin Kuraun Kyanon");
        
        setName("en", "Clown Cannon, Small Trap");
        
		setName("zh_simplified", "小罠 小丑炮");
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
