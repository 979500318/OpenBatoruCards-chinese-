package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.Cost;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R2_TwoOfTamayorihimeTheRoaring extends Card {
    
    public LRIG_R2_TwoOfTamayorihimeTheRoaring()
    {
        setImageSets("WD10-003", "WX25-P1-017","SPDi44-07");
        
        setOriginalName("轟轟　タマヨリヒメ之弐");
        setAltNames("ゴウゴウタマヨリヒメノニ Gougou Tamayorihime no Ni");
        
        setName("en", "Two of Tamayorihime, the Roaring");
        
		setName("zh_simplified", "轰轰 玉依姬之贰");
        setLRIGType(CardLRIGType.TAMA);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
