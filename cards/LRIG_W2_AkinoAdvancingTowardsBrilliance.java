package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_W2_AkinoAdvancingTowardsBrilliance extends Card {
    
    public LRIG_W2_AkinoAdvancingTowardsBrilliance()
    {
        setImageSets("WXDi-P14-074");
        
        setOriginalName("煌めきへ前進　アキノ");
        setAltNames("キラメキヘゼンシンアキノ Kirameki he Zenshin Akino");
        
        setName("en", "Akino, Bound for Brilliance");
        
        
        setName("en_fan", "Akino, Advancing Towards Brilliance");
        
		setName("zh_simplified", "向辉煌前进 昭乃");
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

