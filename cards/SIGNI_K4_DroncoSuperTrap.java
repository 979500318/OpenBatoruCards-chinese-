package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K4_DroncoSuperTrap extends Card {
    
    public SIGNI_K4_DroncoSuperTrap()
    {
        setImageSets("WXK01-101");
        
        setOriginalName("超罠　ドロンコ");
        setAltNames("チョウビンドロンコ Choubin Doronko");
        
        setName("en", "Dronco, Super Trap");
        
		setName("zh_simplified", "超罠 泥浆问答");
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(4);
        setPower(15000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
