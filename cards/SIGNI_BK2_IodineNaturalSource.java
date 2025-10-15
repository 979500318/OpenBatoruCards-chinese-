package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_BK2_IodineNaturalSource extends Card {
    
    public SIGNI_BK2_IodineNaturalSource()
    {
        setImageSets("WXDi-P05-092");
        
        setOriginalName("羅原　I");
        setAltNames("ラゲンヨウソ Ragen Youso");
        
        setName("en", "I, Natural Element");
        
        setName("en_fan", "Iodine, Natural Source");
        
		setName("zh_simplified", "罗原 I");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
