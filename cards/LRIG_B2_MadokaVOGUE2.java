package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_B2_MadokaVOGUE2 extends Card {
    
    public LRIG_B2_MadokaVOGUE2()
    {
        setImageSets("WXDi-P02-029");
        
        setOriginalName("VOGUE2 マドカ");
        setAltNames("ボーグツーマドカ Boogu Tsuu Madoka");
        
        setName("en", "Madoka, Vogue 2");
        
        setName("en_fan", "Madoka, VOGUE 2");
        
		setName("zh_simplified", "VOGUE2 円");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MADOKA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
