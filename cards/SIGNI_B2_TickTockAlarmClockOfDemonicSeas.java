package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_TickTockAlarmClockOfDemonicSeas extends Card {
    
    public SIGNI_B2_TickTockAlarmClockOfDemonicSeas()
    {
        setImageSets("WXK01-085");
        
        setOriginalName("魔海の目覚まし　チクタク");
        setAltNames("マカイノメザマシチクタク Makai no Mezamashi Chikutaku");
        
        setName("en", "Tick-Tock, Alarm Clock of Demonic Seas");
        
		setName("zh_simplified", "魔海的目觉 滴答滴答");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(7000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
