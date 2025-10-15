package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_R0_ExZero extends Card {
    
    public LRIG_R0_ExZero()
    {
        setImageSets("WXDi-D07-001", "SPDi01-19","SPDi21-08","SPDi23-09");
        
        setOriginalName("エクス・ゼロ");
        setAltNames("エクスゼロ Ekusu Zero");
        
        setName("en", "Ex Zero");
        
        setName("en_fan", "Ex Zero");
        
		setName("zh_simplified", "艾克斯·零");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.EX);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
