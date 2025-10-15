package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_MiyakoTsukiyuki extends Card {
    
    public LRIG_B0_MiyakoTsukiyuki()
    {
        setImageSets("WX25-CP1-013");
        
        setOriginalName("月雪ミヤコ");
        setAltNames("ツキユキミヤコ Tsukiyuki Miyako");
        
        setName("en", "Tsukiyuki Miyako");
        
        setName("en_fan", "Miyako Tsukiyuki");
        
		setName("zh_simplified", "月雪宫子");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIYAKO);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
