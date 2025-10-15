package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R0_ZeroOfTamayorihime extends Card {
    
    public LRIG_R0_ZeroOfTamayorihime()
    {
        setImageSets("WD10-005", "WX25-P1-015","SPDi44-05");
        
        setOriginalName("タマヨリヒメ之零");
        setAltNames("タマヨリヒメノゼロ Tamayorihime no Zero");
        
        setName("en", "Zero of Tamayorihime");
        
		setName("zh_simplified", "玉依姬之零");
        setLRIGType(CardLRIGType.TAMA);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
