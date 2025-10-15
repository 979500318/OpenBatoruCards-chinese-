package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_GK1_EinGreenGas extends Card {
    
    public SIGNI_GK1_EinGreenGas()
    {
        setImageSets("WXDi-D06-018");
        
        setOriginalName("アイン＝グリーンガス");
        setAltNames("アイングリーンガス Ain Guriin Gasu");
        
        setName("en", "Green Gas Type: Eins");
        
        setName("en_fan", "Ein-Green Gas");
        
		setName("zh_simplified", "EINS=加压气体");
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
