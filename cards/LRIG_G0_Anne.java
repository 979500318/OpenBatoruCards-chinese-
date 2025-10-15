package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_G0_Anne extends Card {
    
    public LRIG_G0_Anne()
    {
        setImageSets("PR-168", Mask.IGNORE+"WX11-TR04", Mask.IGNORE+"WX19-BM06",Mask.IGNORE+"WX21-GC07", "SPDi34-14");
        
        setOriginalName("アン");
        setAltNames("An");

        setName("en", "Ann");
        
        setName("en_fan", "Anne");
        
		setName("zh_simplified", "安");
        setLRIGType(CardLRIGType.ANN);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
