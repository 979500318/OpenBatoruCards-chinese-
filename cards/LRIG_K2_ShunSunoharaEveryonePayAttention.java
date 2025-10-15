package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_K2_ShunSunoharaEveryonePayAttention extends Card {
    
    public LRIG_K2_ShunSunoharaEveryonePayAttention()
    {
        setImageSets("WX25-CP1-023");
        
        setOriginalName("春原シュン[みなさん、集中してください！]");
        setAltNames("スノハラシュンミナサンシュウチュウシテクダサイ Sunohara Shun Minasan Shuuchuushite Kudasai");
        
        setName("en", "Sunohara Shun [Everyone, pay attention!]");
        
        setName("en_fan", "Shun Sunohara [Everyone, pay attention!]");
        
		setName("zh_simplified", "春原瞬[请大家集中精神！]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SHUN);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
