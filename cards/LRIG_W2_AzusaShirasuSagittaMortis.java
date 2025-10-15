package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_W2_AzusaShirasuSagittaMortis extends Card {
    
    public LRIG_W2_AzusaShirasuSagittaMortis()
    {
        setImageSets("WXDi-CP02-013");
        
        setOriginalName("白洲アズサ[sagitta mortis]");
        setAltNames("シラスアズササジッタモルティス Shirasu Azusa Sajitta Morutisu");
        
        setName("en", "Shirasu Azusa [sagitta mortis]");
        
        
        setName("en_fan", "Azusa Shirasu [sagitta mortis]");
        
		setName("zh_simplified", "白洲梓[sagitta mortis]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AZUSA);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

