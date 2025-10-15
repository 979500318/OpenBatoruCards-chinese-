package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W1_NovaPrologue extends Card {
    
    public LRIG_W1_NovaPrologue()
    {
        setImageSets("WXDi-P03-021");
        
        setOriginalName("前奏　ノヴァ");
        setAltNames("プロローグノヴァ Puroroogu Nova");
        
        setName("en", "Nova, Prelude");
        
        setName("en_fan", "Nova, Prologue");
        
		setName("zh_simplified", "前奏 超");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NOVA);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
