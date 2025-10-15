package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G0_BangNewborn extends Card {
    
    public LRIG_G0_BangNewborn()
    {
        setImageSets("WXDi-D05-008", "SPDi01-15");
        
        setOriginalName("ニューボーン　バン");
        setAltNames("ニューボーンバン Nyuuboon Ban");
        
        setName("en", "Newborn Bang");
        
        setName("en_fan", "Bang, Newborn");
        
		setName("zh_simplified", "新生 梆");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.BANG);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
