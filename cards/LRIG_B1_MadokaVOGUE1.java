package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B1_MadokaVOGUE1 extends Card {
    
    public LRIG_B1_MadokaVOGUE1()
    {
        setImageSets("WXDi-P02-028");
        
        setOriginalName("VOGUE1 マドカ");
        setAltNames("ボーグワンマドカ Boogu Wan Madoka");
        
        setName("en", "Madoka, Vogue 1");
        
        setName("en_fan", "Madoka, VOGUE 1");
        
		setName("zh_simplified", "VOGUE1 円");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MADOKA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
