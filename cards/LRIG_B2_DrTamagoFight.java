package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_B2_DrTamagoFight extends Card {
    
    public LRIG_B2_DrTamagoFight()
    {
        setImageSets("WXDi-D05-003");
        
        setOriginalName("ファイト　Ｄｒ．タマゴ");
        setAltNames("ファイト　Ｄｒ．タマゴ Faito Dokutaa Tamago");
        
        setName("en", "You Can Do It! Dr. Tamago");
        
        setName("en_fan", "Dr. Tamago, Fight");
        
		setName("zh_simplified", "对战 Dr.玉子");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMAGO);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
