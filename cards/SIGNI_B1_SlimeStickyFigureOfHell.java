package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_SlimeStickyFigureOfHell extends Card {
    
    public SIGNI_B1_SlimeStickyFigureOfHell()
    {
        setImageSets("WDK02-016");
        
        setOriginalName("魔界の粘形　スライム");
        setAltNames("マカイノネンギョウスライム Makai no Nengyou Suraimu");
        
        setName("en", "Slime, Sticky Figure of Hell");
        
		setName("zh_simplified", "魔界的粘形 史莱姆");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
