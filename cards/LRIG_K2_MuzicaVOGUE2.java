package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_K2_MuzicaVOGUE2 extends Card {
    
    public LRIG_K2_MuzicaVOGUE2()
    {
        setImageSets("WXDi-D06-003");
        
        setOriginalName("VOGUE2 ムジカ");
        setAltNames("ボーグワンムジカ Boogu Tsuu Mujika");
        
        setName("en", "Muzica, Vogue 2");
        
        setName("en_fan", "Muzica, VOGUE 2");
        
		setName("zh_simplified", "VOGUE2 穆希卡");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MUZICA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
