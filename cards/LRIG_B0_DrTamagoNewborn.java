package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_DrTamagoNewborn extends Card {
    
    public LRIG_B0_DrTamagoNewborn()
    {
        setImageSets("WXDi-D05-001", "SPDi01-13","SPDi05-04");
        
        setOriginalName("ニューボーン　Dr.タマゴ");
        setAltNames("ニューボーンドクタータマゴ Nyuuboon Dokutaa Tamago");
        
        setName("en", "Newborn Dr. Tamago");
        
        setName("en_fan", "Dr. Tamago, Newborn");
        
		setName("zh_simplified", "新生 Dr.玉子");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMAGO);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
