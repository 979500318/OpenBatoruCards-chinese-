package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K2_HeitenMediumTrap extends Card {
    
    public SIGNI_K2_HeitenMediumTrap()
    {
        setImageSets("WXK01-105");
        
        setOriginalName("中罠　ヘイテン");
        setAltNames("チュウビンヘイテン Chuubin Heiten");
        
        setName("en", "Heiten, Medium Trap");
        
		setName("zh_simplified", "中罠 清仓销售");
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(2);
        setPower(7000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
