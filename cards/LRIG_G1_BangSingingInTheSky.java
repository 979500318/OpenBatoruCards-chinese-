package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G1_BangSingingInTheSky extends Card {
    
    public LRIG_G1_BangSingingInTheSky()
    {
        setImageSets("WXDi-P02-021");
        
        setOriginalName("ソラニウタウ　バン");
        setAltNames("ソラニウタウバン Sora ni Utau Ban");
        
        setName("en", "Bang, Singer of Melodies");
        
        setName("en_fan", "Bang, Singing in the Sky");
        
		setName("zh_simplified", "空中唱 梆");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.BANG);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
