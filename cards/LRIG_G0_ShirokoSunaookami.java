package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G0_ShirokoSunaookami extends Card {
    
    public LRIG_G0_ShirokoSunaookami()
    {
        setImageSets("WXDi-CP02-017", "SPDi31-03","SPDi32-03");
        
        setOriginalName("砂狼シロコ");
        setAltNames("スナオオカミシロコ Sunaookami Shiroko");
        
        setName("en", "Sunaookami Shiroko");
        
        
        setName("en_fan", "Shiroko Sunaookami");
        
		setName("zh_simplified", "砂狼白子");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SHIROKO);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

