package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_GoblinPhantomApparition extends Card {
    
    public SIGNI_G1_GoblinPhantomApparition()
    {
        setImageSets("WDK03-016");
        
        setOriginalName("幻怪　ゴブリン");
        setAltNames("ゲンカイゴブリン Genkai Goburin");
        
        setName("en", "Goblin, Phantom Apparition");
        
		setName("zh_simplified", "幻怪 哥布林");
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
