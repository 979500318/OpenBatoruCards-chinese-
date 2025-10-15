package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G0_VJWOLFStandby extends Card {
    
    public LRIG_G0_VJWOLFStandby()
    {
        setImageSets("WXDi-D04-008", "SPDi01-12","SPDi11-02");
        
        setOriginalName("VJ.WOLF-standby");
        setAltNames("ブイジェーウルフスタンバイ Buijee Urufu Sutanbai");
        
        setName("en", "VJ WOLF - Standby");
        
        setName("en_fan", "VJ.WOLF - standby");
        
		setName("zh_simplified", "VJ.WOLF-standby");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.WOLF);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
