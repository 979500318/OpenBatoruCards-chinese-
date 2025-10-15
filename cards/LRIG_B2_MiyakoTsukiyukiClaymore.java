package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_B2_MiyakoTsukiyukiClaymore extends Card {
    
    public LRIG_B2_MiyakoTsukiyukiClaymore()
    {
        setImageSets("WX25-CP1-015");
        
        setOriginalName("月雪ミヤコ[クレイモア]");
        setAltNames("ツキユキミヤコクレイモア Tsukiyuki Miyako Kureimoa");
        
        setName("en", "Tsukiyuki Miyako [Claymore]");
        
        setName("en_fan", "Miyako Tsukiyuki [Claymore]");
        
		setName("zh_simplified", "月雪宫子[阔剑地雷]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIYAKO);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
