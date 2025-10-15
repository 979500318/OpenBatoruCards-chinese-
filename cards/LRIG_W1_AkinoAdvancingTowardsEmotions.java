package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W1_AkinoAdvancingTowardsEmotions extends Card {
    
    public LRIG_W1_AkinoAdvancingTowardsEmotions()
    {
        setImageSets("WXDi-P02-007");
        
        setOriginalName("想いへ前進　アキノ");
        setAltNames("オモイヘゼンシンアキノ Omoi he Zenshin Akino");
        
        setName("en", "Akino, Bound for Dreams");
        
        setName("en_fan", "Akino, Advancing Towards Emotions");
        
		setName("zh_simplified", "向梦想前进 昭乃");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AKINO);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
