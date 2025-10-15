package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K1_AlfouBrideOfBlackHeart extends Card {
    
    public LRIG_K1_AlfouBrideOfBlackHeart()
    {
        setImageSets("WX24-P3-028");
        
        setOriginalName("黒心の花嫁　アルフォウ");
        setAltNames("コクシンノハナヨメアルフォウ Kokushin no Hanayome Arufou");
        
        setName("en", "Alfou, Bride of Black Heart");
        
		setName("zh_simplified", "黑心的花嫁 阿尔芙");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ALFOU);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
