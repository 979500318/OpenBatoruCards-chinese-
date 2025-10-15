package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B1_MiyakoTsukiyukiOperationReinforcements extends Card {
    
    public LRIG_B1_MiyakoTsukiyukiOperationReinforcements()
    {
        setImageSets("WX25-CP1-014");
        
        setOriginalName("月雪ミヤコ[攪乱作戦]");
        setAltNames("ツキユキミヤコカクランサクセン Tsukiyuki Miyako Kakuran Sakusen");
        
        setName("en", "Tsukiyuki Miyako [Operation Reinforcements]");
        
        setName("en_fan", "Miyako Tsukiyuki [Operation Reinforcements]");
        
		setName("zh_simplified", "月雪宫子[干扰作战]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIYAKO);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
