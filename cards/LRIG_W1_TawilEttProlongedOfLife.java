package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W1_TawilEttProlongedOfLife extends Card {
    
    public LRIG_W1_TawilEttProlongedOfLife()
    {
        setImageSets("WX06-009","WXDi-P16-015", "WX25-P1-012","SPDi44-10", "SP33-007");
        
        setOriginalName("永らえし者　タウィル＝エット");
        setAltNames("ナガラエシモノタウィルエット Nagarae Shimono Tauiru Etto");
        
        setName("en", "Tawil =Ett=, Ancient One");
        
        
        setName("en_fan", "Tawil-Ett, Prolonged of Life");
        
		setName("zh_simplified", "永生者 塔维尔＝ETT");
        setLRIGType(CardLRIGType.TAWIL);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
