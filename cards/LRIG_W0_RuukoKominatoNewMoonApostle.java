package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W0_RuukoKominatoNewMoonApostle extends Card {

    public LRIG_W0_RuukoKominatoNewMoonApostle()
    {
        setImageSets("WX24-P2-011", Mask.IGNORE+"SPDi42-1P");
        
        setOriginalName("新月の使徒　小湊るう子");
        setAltNames("シンゲツノシトコミナトルウコ Shingetsu no Shito Kominato Ruuko");
        
        setName("en", "Ruuko Kominato, New Moon Apostle");
        
		setName("zh_simplified", "新月的使徒 小凑露子");
        setLRIGType(CardLRIGType.RUUKO);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
