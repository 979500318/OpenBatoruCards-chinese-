package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WR2_OriasHolyCrimsonDevil extends Card {
    
    public SIGNI_WR2_OriasHolyCrimsonDevil()
    {
        setImageSets("WXDi-P04-088");
        
        setOriginalName("聖紅魔　オリアス");
        setAltNames("セイクマオリアス Seikuma Orias");
        
        setName("en", "Orias, Blessed Crimson Evil");
        
        setName("en_fan", "Orias, Holy Crimson Devil");
        
		setName("zh_simplified", "圣红魔 欧里亚斯");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
