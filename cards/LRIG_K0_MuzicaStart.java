package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K0_MuzicaStart extends Card {
    
    public LRIG_K0_MuzicaStart()
    {
        setImageSets("WXDi-D06-001", "SPDi01-16","SPDi05-05","SPDi11-04");
        
        setOriginalName("ムジカ　ＳＴＡＲＴ");
        setAltNames("ムジカスタート Mujika Sutaato");
        
        setName("en", "Muzica START");
        
        setName("en_fan", "Muzica START");
        
		setName("zh_simplified", "穆希卡 START");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MUZICA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
