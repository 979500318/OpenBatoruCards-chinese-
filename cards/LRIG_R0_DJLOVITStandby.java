package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_R0_DJLOVITStandby extends Card {
    
    public LRIG_R0_DJLOVITStandby()
    {
        setImageSets("WXDi-D04-005", "SPDi01-11");
        
        setOriginalName("DJ.LOVIT-standby");
        setAltNames("ディージェーラビットスタンバイ Dii Jee Rabitto Sutanbai");
        
        setName("en", "DJ LOVIT - Standby");
        
        setName("en_fan", "DJ.LOVIT - standby");
        
		setName("zh_simplified", "DJ.LOVIT-standby");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LOVIT);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
