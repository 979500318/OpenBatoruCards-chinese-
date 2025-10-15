package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_NovaNewBorn extends Card {
    
    public LRIG_W0_NovaNewBorn()
    {
        setImageSets("WXDi-D05-005", "SPDi01-14","SPDi11-03");
        
        setOriginalName("ニューボーン　ノヴァ");
        setAltNames("ニューボーンノヴァ Nyuuboon Nova");
        
        setName("en", "Newborn Nova");
        
        setName("en_fan", "Nova, Newborn");
        
		setName("zh_simplified", "新生 超");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NOVA);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
