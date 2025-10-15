package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_AkinoAdvancingTowardsTheFuture extends Card {
    
    public LRIG_W0_AkinoAdvancingTowardsTheFuture()
    {
        setImageSets("WXDi-D03-005", "SPDi01-08","SPDi02-05","SPDi04-03","SPDi06-02","SPDi23-01","SPDi28-01");
        
        setOriginalName("未来へ前進　アキノ");
        setAltNames("ミライヘゼンシンアキノ Mirai he Zenshin Akino");
        
        setName("en", "Akino, Bound for the Future");
        
        setName("en_fan", "Akino, Advancing Towards the Future");
        
		setName("zh_simplified", "向未来前进 昭乃");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AKINO);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
