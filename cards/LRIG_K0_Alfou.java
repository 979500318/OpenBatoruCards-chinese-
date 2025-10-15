package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K0_Alfou extends Card {
    
    public LRIG_K0_Alfou()
    {
        setImageSets("PR-299", Mask.IGNORE+"WX12-TR04");
        
        setOriginalName("アルフォウ");
        setAltNames("Arufou");
        
        setName("en", "Alfou");
        
		setName("zh_simplified", "阿尔芙");
        setLRIGType(CardLRIGType.ALFOU);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
