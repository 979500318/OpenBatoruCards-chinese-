package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B1_ReiSoaringTowardsTruth extends Card {
    
    public LRIG_B1_ReiSoaringTowardsTruth()
    {
        setImageSets("WXDi-P03-007");
        
        setOriginalName("真理へ飛翔　レイ");
        setAltNames("シンリヘヒショウレイ Shinri he Hishou Rei");
        
        setName("en", "Rei, On the Wings of Truth");
        
        setName("en_fan", "Rei, Soaring Towards Truth");
        
		setName("zh_simplified", "向真理飞翔 令");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REI);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
