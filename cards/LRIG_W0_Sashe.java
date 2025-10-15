package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W0_Sashe extends Card {
    
    public LRIG_W0_Sashe()
    {
        setImageSets("PR-300", Mask.IGNORE+"WX13-TR01", Mask.IGNORE+"WX19-BM02");
        
        setOriginalName("サシェ");
        setAltNames("Sashe");
        
        setName("en", "Sashe");
        
		setName("zh_simplified", "莎榭");
        setLRIGType(CardLRIGType.SASHE);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
