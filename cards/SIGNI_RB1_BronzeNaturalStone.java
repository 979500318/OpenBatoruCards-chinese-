package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RB1_BronzeNaturalStone extends Card {
    
    public SIGNI_RB1_BronzeNaturalStone()
    {
        setImageSets("WXDi-P00-083", "SPDi38-35");
        
        setOriginalName("羅石　ブロンズ");
        setAltNames("ラセキブロンズ Raseki Buronzu");
        
        setName("en_fan", "Bronze, Natural Crystal");
        
        setName("en", "Bronze, Natural Stone");
        
		setName("zh_simplified", "罗石 青铜");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
