package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WR2_GawainHolyCrimsonGeneral extends Card {
    
    public SIGNI_WR2_GawainHolyCrimsonGeneral()
    {
        setImageSets("WXDi-P01-090");
        
        setOriginalName("聖紅将　ガウェイン");
        setAltNames("セイクショウガウェイン Seikushou Gauein");
        
        setName("en", "Gawain, Blessed Crimson General");
        
        setName("en_fan", "Gawain, Holy Crimson General");
        
		setName("zh_simplified", "圣红将 高文");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
