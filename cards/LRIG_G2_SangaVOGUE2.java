package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_G2_SangaVOGUE2 extends Card {
    
    public LRIG_G2_SangaVOGUE2()
    {
        setImageSets("WXDi-P03-029");
        
        setOriginalName("VOGUE2 サンガ");
        setAltNames("ボーグワンサンガ Boogu Tsuu Sanga");
        
        setName("en", "Sanga, Vogue 2");
        
        setName("en_fan", "Sanga, VOGUE 2");
        
		setName("zh_simplified", "VOGUE2 山河");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SANGA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
