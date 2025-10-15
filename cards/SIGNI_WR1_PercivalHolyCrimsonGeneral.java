package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WR1_PercivalHolyCrimsonGeneral extends Card {
    
    public SIGNI_WR1_PercivalHolyCrimsonGeneral()
    {
        setImageSets("WXDi-P01-089");
        
        setOriginalName("聖紅将　パシバル");
        setAltNames("セイクショウパシバル Seikushou Pashibaru");
        
        setName("en", "Percival, Blessed Crimson General");
        
        setName("en_fan", "Percival, Holy Crimson General");
        
		setName("zh_simplified", "圣红将 帕西瓦尔");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
