package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W1_TamayorihimeWaxingCrescentMoonMiko extends Card {
    
    public LRIG_W1_TamayorihimeWaxingCrescentMoonMiko()
    {
        setImageSets(Mask.IGNORE+"WD01-004", "WXDi-D08-002", "WX24-D1-02", "SP33-003");
        
        setOriginalName("三日月の巫女　タマヨリヒメ");
        setAltNames("ミカヅキノミコタマヨリヒメ Mikazuki no Miko Tamayorihime");
        
        setName("en", "Tamayorihime, Crescent Moon Miko");
        
        setName("en_fan", "Tamayorihime, Waxing Crescent Moon Miko");
        
		setName("zh_simplified", "三日月的巫女 玉依姬");
        setLRIGType(CardLRIGType.TAMA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
