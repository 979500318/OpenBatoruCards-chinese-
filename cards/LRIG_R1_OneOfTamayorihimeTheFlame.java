package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R1_OneOfTamayorihimeTheFlame extends Card {
    
    public LRIG_R1_OneOfTamayorihimeTheFlame()
    {
        setImageSets("WD10-004", "WX25-P1-016","SPDi44-06");
        
        setOriginalName("焔　タマヨリヒメ之壱");
        setAltNames("ホムラタマヨリヒメノイチ Homura Tamayorihime no Ichi");
        
        setName("en", "One of Tamayorihime, the Flame");
        
		setName("zh_simplified", "焰 玉依姬之壹");
        setLRIGType(CardLRIGType.TAMA);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
