package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W1_AkinoAdvancingTowardsDetermination extends Card {
    
    public LRIG_W1_AkinoAdvancingTowardsDetermination()
    {
        setImageSets("WXDi-P14-073");
        
        setOriginalName("決意へ前進　アキノ");
        setAltNames("ケツイヘゼンシンアキノ Ketsui he Zenshin Akino");
        
        setName("en", "Akino, Bound for Resolve");
        
        
        setName("en_fan", "Akino, Advancing towards Determination");
        
		setName("zh_simplified", "向决意前进 昭乃");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AKINO);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

