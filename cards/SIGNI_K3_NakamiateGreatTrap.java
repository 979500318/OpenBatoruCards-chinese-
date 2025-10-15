package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K3_NakamiateGreatTrap extends Card {
    
    public SIGNI_K3_NakamiateGreatTrap()
    {
        setImageSets("WXK01-103");
        
        setOriginalName("大罠　ナカミアテ");
        setAltNames("ダイビンナカミアテ Daibin Nakamiate");
        
        setName("en", "Nakamiate, Great Trap");
        
		setName("zh_simplified", "大罠 黑箱摸物");
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
