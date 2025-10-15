package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_GK2_ZweiCobras extends Card {
    
    public SIGNI_GK2_ZweiCobras()
    {
        setImageSets("WXDi-P01-098");
        
        setOriginalName("ツヴァイ＝コブラス");
        setAltNames("ツヴァイコブラス Tsuvai Koburasu");
        
        setName("en", "Cobra Type: Zwei");
        
        setName("en_fan", "Zwei-Cobras");
        
		setName("zh_simplified", "ZWEI=眼镜蛇蛇");
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
