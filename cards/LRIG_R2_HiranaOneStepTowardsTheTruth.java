package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_R2_HiranaOneStepTowardsTheTruth extends Card {
    
    public LRIG_R2_HiranaOneStepTowardsTheTruth()
    {
        setImageSets("WXDi-D03-003");
        
        setOriginalName("真実へ一歩　ヒラナ");
        setAltNames("シンジツヘイッポヒラナ Shinjitsu he Ippo Hirana");
        
        setName("en", "Hirana, a Glimpse of the Truth");
        
        setName("en_fan", "Hirana, One Step Towards the Truth");
        
		setName("zh_simplified", "向真实一步 平和");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HIRANA);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
