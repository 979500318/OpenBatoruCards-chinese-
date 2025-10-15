package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K0_DeusZero extends Card {
    
    public LRIG_K0_DeusZero()
    {
        setImageSets("WXDi-D07-005", "SPDi01-20","SPDi23-08");
        
        setOriginalName("デウス・ゼロ");
        setAltNames("デウスゼロ Deusu Zero");
        
        setName("en", "Deus Zero");
        
        setName("en_fan", "Deus Zero");
        
		setName("zh_simplified", "迪乌斯·零");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.DEUS);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
