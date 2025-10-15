package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_B2_ReiSoaringTowardsTheBlueSky extends Card {
    
    public LRIG_B2_ReiSoaringTowardsTheBlueSky()
    {
        setImageSets("WXDi-P03-008");
        
        setOriginalName("蒼穹へ飛翔　レイ");
        setAltNames("ソウキュウヘヒショウレイ Soukyuu he Hishou Rei");
        
        setName("en", "Rei, On the Wings of Azure Sky");
        
        setName("en_fan", "Rei, Soaring Towards the Blue Sky");
        
		setName("zh_simplified", "向苍穹飞翔 令");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REI);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
