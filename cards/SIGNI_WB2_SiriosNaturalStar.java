package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WB2_SiriosNaturalStar extends Card {
    
    public SIGNI_WB2_SiriosNaturalStar()
    {
        setImageSets("WXDi-D05-019");
        
        setOriginalName("羅星　シリオス");
        setAltNames("ラセイシリオス Rasei Shiriosu");
        
        setName("en", "Sirius, Natural Planet");
        
        setName("en_fan", "Sirios, Natural Star");
        
		setName("zh_simplified", "罗星 大犬座α");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
