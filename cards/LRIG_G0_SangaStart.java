package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G0_SangaStart extends Card {
    
    public LRIG_G0_SangaStart()
    {
        setImageSets("WXDi-D06-008", "SPDi01-18");
        
        setOriginalName("サンガ　ＳＴＡＲＴ");
        setAltNames("サンガスタート Sanga Sutaato");
        
        setName("en", "Sanga START");
        
        setName("en_fan", "Sanga START");
        
		setName("zh_simplified", "山河 START");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SANGA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
