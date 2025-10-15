package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R1_YuzukiOneTheBlaze extends Card {
    
    public LRIG_R1_YuzukiOneTheBlaze()
    {
        setImageSets("WX24-P2-016");
        
        setOriginalName("炎　遊月・壱");
        setAltNames("エンユヅキイチ En Yuzuki Ichi");
        
        setName("en", "Yuzuki One, the Blaze");
        
		setName("zh_simplified", "炎 游月·壹");
        setLRIGType(CardLRIGType.YUZUKI);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
