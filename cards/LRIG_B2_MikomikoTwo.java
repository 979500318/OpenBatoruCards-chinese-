package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_B2_MikomikoTwo extends Card {
    
    public LRIG_B2_MikomikoTwo()
    {
        setImageSets("WXDi-P05-013");
        
        setOriginalName("みこみこ☆にっ");
        setAltNames("ミコミコ二ッ Mikomiko Ni Mikomiko Two");
        
        setName("en", "Mikomiko☆Two");
        
        setName("en_fan", "Mikomiko☆Two");
        
		setName("zh_simplified", "美琴琴☆贰");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIKOMIKO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
