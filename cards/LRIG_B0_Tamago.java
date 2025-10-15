package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_Tamago extends Card {
    
    public LRIG_B0_Tamago()
    {
        setImageSets("SPDi03-07", "SPDi34-21");
        
        setOriginalName("タマゴ");
        setAltNames("Tamago");
        
        setName("en", "Tamago");
        
		setName("zh_simplified", "玉子");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMAGO);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
