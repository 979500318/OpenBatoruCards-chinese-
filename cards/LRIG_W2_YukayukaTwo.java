package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_W2_YukayukaTwo extends Card {
    
    public LRIG_W2_YukayukaTwo()
    {
        setImageSets("WXDi-P08-012");
        
        setOriginalName("ゆかゆか☆にっ");
        setAltNames("ユカユカ二ッ Yukayuka Ni Yukayuka Two");
        
        setName("en", "Yukayuka☆Two");
        
        setName("en_fan", "Yukayuka☆Two");
        
		setName("zh_simplified", "由香香☆贰");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUKAYUKA);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
