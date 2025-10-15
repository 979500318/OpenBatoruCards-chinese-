package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_G0_Aiyai extends Card {
    
    public LRIG_G0_Aiyai()
    {
        setImageSets("PR-302", Mask.IGNORE+"WX13-TR04", Mask.IGNORE+"WX19-BM07",Mask.IGNORE+"WX21-GC08");
        
        setOriginalName("アイヤイ");
        setAltNames("Aiyai");
        
        setName("en", "Aiyai");
        
		setName("zh_simplified", "艾娅伊");
        setLRIGType(CardLRIGType.AIYAI);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
