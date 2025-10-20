package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_MCLIONStandby extends Card {
    
    public LRIG_W0_MCLIONStandby()
    {
        setImageSets("WXDi-D04-001", "SPDi01-10","SPDi05-03","SPDi42-9P");
        
        setOriginalName("MC.LION-standby");
        setAltNames("エムシーリオンスタンバイ Emu Shii Rion Sutanbai");
        
        setName("en", "MC LION - Standby");
        
        setName("en_fan", "MC.LION - standby");
        
		setName("zh_simplified", "MC.LION-standby");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
