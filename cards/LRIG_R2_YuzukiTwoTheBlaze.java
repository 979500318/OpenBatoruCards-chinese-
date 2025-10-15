package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_R2_YuzukiTwoTheBlaze extends Card {
    
    public LRIG_R2_YuzukiTwoTheBlaze()
    {
        setImageSets("WX24-P2-017");
        
        setOriginalName("炎　遊月・弐");
        setAltNames("エンユヅキニ En Yuzuki Ni");
        
        setName("en", "Yuzuki Two, the Blaze");
        
		setName("zh_simplified", "炎 游月·贰");
        setLRIGType(CardLRIGType.YUZUKI);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
