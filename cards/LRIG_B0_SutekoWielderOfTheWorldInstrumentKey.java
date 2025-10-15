package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_SutekoWielderOfTheWorldInstrumentKey extends Card {
    
    public LRIG_B0_SutekoWielderOfTheWorldInstrumentKey()
    {
        setImageSets("PR-K053");
        
        setOriginalName("奏世の鍵主　ステコ");
        setAltNames("ソウセイノカギヌシステコ Soosei no Kaginushi Suteko");
        
        setName("en", "Suteko, Wielder of the World Instrument Key");
        
		setName("zh_simplified", "奏世的键主 寿子");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.UMR);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
