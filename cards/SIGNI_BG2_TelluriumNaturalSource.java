package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_BG2_TelluriumNaturalSource extends Card {
    
    public SIGNI_BG2_TelluriumNaturalSource()
    {
        setImageSets("WXDi-P06-091");
        
        setOriginalName("羅原　Te");
        setAltNames("ラゲンテルル Ragen Teruru");
        
        setName("en", "Te, Natural Element");
        
        setName("en_fan", "Tellurium, Natural Source");
        
		setName("zh_simplified", "罗原 Te");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
