package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_BK1_SeleniumNaturalSource extends Card {
    
    public SIGNI_BK1_SeleniumNaturalSource()
    {
        setImageSets("WXDi-P05-091");
        
        setOriginalName("羅原　Se");
        setAltNames("ラゲンセレン Ragen Seren");
        
        setName("en", "Se, Natural Element");
        
        setName("en_fan", "Selenium, Natural Source");
        
		setName("zh_simplified", "罗原 Se");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
