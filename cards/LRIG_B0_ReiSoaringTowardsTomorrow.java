package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_ReiSoaringTowardsTomorrow extends Card {
    
    public LRIG_B0_ReiSoaringTowardsTomorrow()
    {
        setImageSets("WXDi-D03-008", "SPDi01-09","SPDi04-02","SPDi06-03");
        
        setOriginalName("明日へ飛翔　レイ");
        setAltNames("アスヘヒショウレイ Asu he Hishoo Rei");
        
        setName("en", "Rei, On the Wings of Tomorrow");
        
        setName("en_fan", "Rei, Soaring Towards Tomorrow");
        
		setName("zh_simplified", "向明日飞翔 令");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REI);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
