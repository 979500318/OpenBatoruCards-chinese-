package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G1_SangaVOGUE1 extends Card {
    
    public LRIG_G1_SangaVOGUE1()
    {
        setImageSets("WXDi-P03-028");
        
        setOriginalName("VOGUE1 サンガ");
        setAltNames("ボーグワンサンガ Boogu Wan Sanga");
        
        setName("en", "Sanga, Vogue 1");
        
        setName("en_fan", "Sanga, VOGUE 1");
        
		setName("zh_simplified", "VOGUE1 山河");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SANGA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
