package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RG1_CanesVenaNaturalStar extends Card {
    
    public SIGNI_RG1_CanesVenaNaturalStar()
    {
        setImageSets("WXDi-P06-088");
        
        setOriginalName("羅星　カネスヴィナ");
        setAltNames("ラセイカネスヴィナ Rasei Kanesvina");
        
        setName("en", "Canes Vena, Natural Planet");
        
        setName("en_fan", "Canes Vena, Natural Star");
        
		setName("zh_simplified", "罗星 猎犬座");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
