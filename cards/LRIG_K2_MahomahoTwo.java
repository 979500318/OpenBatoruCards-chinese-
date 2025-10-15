package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_K2_MahomahoTwo extends Card {
    
    public LRIG_K2_MahomahoTwo()
    {
        setImageSets("WXDi-P07-012");
        
        setOriginalName("まほまほ☆にっ");
        setAltNames("マホマホ二ッ Mahomaho Ni Mahomaho Two");
        
        setName("en", "Mahomaho☆Two");
        
        setName("en_fan", "Mahomaho☆Two");
        
		setName("zh_simplified", "真帆帆☆贰");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MAHOMAHO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
