package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W0_TawilNollProlongedOfLife extends Card {
    
    public LRIG_W0_TawilNollProlongedOfLife()
    {
        setImageSets(Mask.IGNORE+"WX06-010", "WXDi-P16-014", "WX25-P1-011","SPDi44-09", "SP16-006","SP34-001", "PR-197","PR-233");
        
        setOriginalName("永らえし者　タウィル＝ノル");
        setAltNames("ナガラエシモノタウィルノル Nagarae Shimono Tauiru Noru");
        
        setName("en", "Tawil =Noll=, Ancient One");
        
        
        setName("en_fan", "Tawil-Noll, Prolonged of Life");
        
		setName("zh_simplified", "永生者 塔维尔＝NOLL");
        setLRIGType(CardLRIGType.TAWIL);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
