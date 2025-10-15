package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_W2_AkinoAdvancingTowardsRadiance extends Card {
    
    public LRIG_W2_AkinoAdvancingTowardsRadiance()
    {
        setImageSets("WXDi-P02-008");
        
        setOriginalName("輝きへ前進　アキノ");
        setAltNames("カガヤキヘゼンシンアキノ Kagayaki he Zenshin Akino");
        
        setName("en", "Akino, Bound for Brightness");
        
        setName("en_fan", "Akino, Advancing Towards Radiance");
        
		setName("zh_simplified", "向光辉前进 昭乃");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AKINO);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
