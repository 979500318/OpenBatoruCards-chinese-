package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W1_AzusaShirasuTargetWeakness extends Card {
    
    public LRIG_W1_AzusaShirasuTargetWeakness()
    {
        setImageSets("WXDi-CP02-012");
        
        setOriginalName("白洲アズサ[弱点を狙う]");
        setAltNames("シラスアズサジャクテンヲネラウ Shirasu Azusa Jakuten wo Nerau");
        
        setName("en", "Shirasu Azusa [Kick 'em While They're Down]");
        
        
        setName("en_fan", "Azusa Shirasu [target weakness]");
        
		setName("zh_simplified", "白洲梓[瞄准弱点]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AZUSA);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

