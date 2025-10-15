package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_W2_RuukoKominatoHalfMoonApostle extends Card {

    public LRIG_W2_RuukoKominatoHalfMoonApostle()
    {
        setImageSets("WX24-P2-013");
        
        setOriginalName("半月の使徒　小湊るう子");
        setAltNames("ハンゲツノシトコミナトルウコ Hangetsu no Shito Kominato Ruuko");
        
        setName("en", "Ruuko Kominato, Half Moon Apostle");
        
		setName("zh_simplified", "半月的使徒 小凑露子");
        setLRIGType(CardLRIGType.RUUKO);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
