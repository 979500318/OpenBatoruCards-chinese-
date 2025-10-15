package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G1_ShirokoSunaookamiHiRapidFire extends Card {
    
    public LRIG_G1_ShirokoSunaookamiHiRapidFire()
    {
        setImageSets("WXDi-CP02-018");
        
        setOriginalName("砂狼シロコ[高速連射]");
        setAltNames("スナオオカミシロココウソクレンシャ Sunaookami Shiroko Kousoku Rensha");
        
        setName("en", "Sunaookami Shiroko [Very Rapid Fire]");
        
        
        setName("en_fan", "Shiroko Sunaookami [Hi-Rapid Fire]");
        
		setName("zh_simplified", "砂狼白子[高速连射]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SHIROKO);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

