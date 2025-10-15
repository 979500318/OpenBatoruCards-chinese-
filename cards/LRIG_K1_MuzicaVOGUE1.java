package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K1_MuzicaVOGUE1 extends Card {
    
    public LRIG_K1_MuzicaVOGUE1()
    {
        setImageSets("WXDi-D06-002");
        
        setOriginalName("VOGUE1 ムジカ");
        setAltNames("ボーグワンムジカ Boogu Wan Mujika");
        
        setName("en", "Muzica, Vogue 1");
        
        setName("en_fan", "Muzica, VOGUE 1");
        
		setName("zh_simplified", "VOGUE1 穆希卡");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MUZICA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
