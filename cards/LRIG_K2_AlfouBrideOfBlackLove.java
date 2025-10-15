package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_K2_AlfouBrideOfBlackLove extends Card {
    
    public LRIG_K2_AlfouBrideOfBlackLove()
    {
        setImageSets("WX24-P3-029");
        
        setOriginalName("黒恋の花嫁　アルフォウ");
        setAltNames("コクレンノハナヨメアルフォウ Kokuren no Hanayome Arufou");
        
        setName("en", "Alfou, Bride of Black Love");
        
		setName("zh_simplified", "黑恋的花嫁 阿尔芙");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ALFOU);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
