package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W1_RuukoKominatoCrescentMoonApostle extends Card {

    public LRIG_W1_RuukoKominatoCrescentMoonApostle()
    {
        setImageSets("WX24-P2-012");
        
        setOriginalName("三日月の使徒　小湊るう子");
        setAltNames("ミカヅキノシトコミナトルウコ Mikazuki no Shito Kominato Ruuko");
        
        setName("en", "Ruuko Kominato, Crescent Moon Apostle");
        
		setName("zh_simplified", "三日月的使徒 小凑露子");
        setLRIGType(CardLRIGType.RUUKO);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
