package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_BG1_TechnetiumNaturalSource extends Card {
    
    public SIGNI_BG1_TechnetiumNaturalSource()
    {
        setImageSets("WXDi-P06-090");
        
        setOriginalName("羅原　Tc");
        setAltNames("ラゲンテクネチウム Ragen Tekunechiumu");
        
        setName("en", "Tc, Natural Element");
        
        setName("en_fan", "Technetium, Natural Source");
        
		setName("zh_simplified", "罗原 Tc");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
