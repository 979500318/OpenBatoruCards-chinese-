package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B1_DrTamagoLetsGo extends Card {
    
    public LRIG_B1_DrTamagoLetsGo()
    {
        setImageSets("WXDi-D05-002");
        
        setOriginalName("レッツゴー　Ｄｒ．タマゴ");
        setAltNames("レッツゴードクタータマゴ Rettsu Goo Dokutaa Tamago");
        
        setName("en", "Let's Go! Dr. Tamago");
        
        setName("en_fan", "Dr. Tamago, Let's Go");
        
		setName("zh_simplified", "出发 Dr.玉子");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMAGO);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
