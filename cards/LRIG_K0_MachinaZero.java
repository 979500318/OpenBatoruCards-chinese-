package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K0_MachinaZero extends Card {
    
    public LRIG_K0_MachinaZero()
    {
        setImageSets("WXDi-D07-008", "SPDi01-21","SPDi23-10");
        
        setOriginalName("マキナ・ゼロ");
        setAltNames("マキナゼロ Makina Zero");
        
        setName("en", "Machina Zero");
        
        setName("en_fan", "Machina Zero");
        
		setName("zh_simplified", "玛琪娜·零");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MACHINA);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
