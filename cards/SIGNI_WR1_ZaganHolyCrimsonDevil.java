package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WR1_ZaganHolyCrimsonDevil extends Card {
    
    public SIGNI_WR1_ZaganHolyCrimsonDevil()
    {
        setImageSets("WXDi-P04-087", "SPDi38-31");
        
        setOriginalName("聖紅魔　ザガン");
        setAltNames("セイクマザガン Seikuma Zagan");
        
        setName("en", "Zagan, Blessed Crimson Evil");
        
        setName("en_fan", "Zagan, Holy Crimson Devil");
        
		setName("zh_simplified", "圣红魔 赛共");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
