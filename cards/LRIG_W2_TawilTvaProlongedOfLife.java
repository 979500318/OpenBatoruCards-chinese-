package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.Cost;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W2_TawilTvaProlongedOfLife extends Card {
    
    public LRIG_W2_TawilTvaProlongedOfLife()
    {
        setImageSets("WX06-008","WXDi-P16-016", "WX25-P1-013","SPDi44-11", "SP33-006");
        
        setOriginalName("永らえし者　タウィル＝トヴォ");
        setAltNames("ナガラエシモノタウィルトヴォ Nagarae Shimono Tauiru Tovuo");
        
        setName("en", "Tawil =Tv\u00e5=, Ancient One");
        
        
        setName("en_fan", "Tawil-Två, Prolonged of Life");
        
		setName("zh_simplified", "永生者 塔维尔＝TVA");
        setLRIGType(CardLRIGType.TAWIL);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
