package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RB2_SilvernaNaturalStone extends Card {
    
    public SIGNI_RB2_SilvernaNaturalStone()
    {
        setImageSets("WXDi-P00-084");
        
        setOriginalName("羅石　シルヴァーナ");
        setAltNames("ラセキシルヴァーナ Raseki Shiruvaana");
        
        setName("en", "Silvana, Natural Crystal");
        
        setName("en_fan", "Silverna, Natural Stone");
        
		setName("zh_simplified", "罗石 银蜡石");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
