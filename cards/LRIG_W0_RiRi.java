package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W0_RiRi extends Card {
    
    public LRIG_W0_RiRi()
    {
        setImageSets("PR-210");
        
        setOriginalName("ＲｉＲｉ");
        setAltNames("リリ Riri");
        
        setName("en", "RiRi");
        
		setName("zh_simplified", "RIRI");
        setLRIGType(CardLRIGType.SASHE);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
