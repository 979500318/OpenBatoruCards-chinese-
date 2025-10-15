package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_MakeUpWorkClub extends Card {
    
    public LRIG_W0_MakeUpWorkClub()
    {
        setImageSets("WXDi-CP02-023");
        
        setOriginalName("補習授業部");
        setAltNames("ホシュウジュギョウブ Hoshuu Jugyoubu");
        
        setName("en", "Make-Up Work Club");
        
        
        setName("en_fan", "Make-Up Work Club");
        
		setName("zh_simplified", "补习授业部");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MAKEUP_WORK_CLUB);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

