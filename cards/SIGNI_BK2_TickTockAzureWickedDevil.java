package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_BK2_TickTockAzureWickedDevil extends Card {
    
    public SIGNI_BK2_TickTockAzureWickedDevil()
    {
        setImageSets("WXDi-D06-019");
        
        setOriginalName("蒼凶魔　チクタク");
        setAltNames("ソウキョウマチクタク Soukyouma Chikutaku");
        
        setName("en", "Tick - Tock, Azure Doomed Evil");
        
        setName("en_fan", "Tick-Tock, Azure Wicked Devil");
        
		setName("zh_simplified", "苍凶魔 滴答滴答");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
