package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_BK1_LittleOniAzureWickedDevil extends Card {
    
    public SIGNI_BK1_LittleOniAzureWickedDevil()
    {
        setImageSets("WXDi-P01-095", "SPDi38-39");
        
        setOriginalName("蒼凶魔　コオニ");
        setAltNames("ソウキョウマコオニ Soukyouma Kooni");
        
        setName("en", "Imp, Azure Doomed Evil");
        
        setName("en_fan", "Little Oni, Azure Wicked Devil");
        
		setName("zh_simplified", "苍凶魔 小鬼");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
