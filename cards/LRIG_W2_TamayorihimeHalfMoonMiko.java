package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W2_TamayorihimeHalfMoonMiko extends Card {
    
    public LRIG_W2_TamayorihimeHalfMoonMiko()
    {
        setImageSets(Mask.IGNORE+"WD01-003", "WXDi-D08-003", "WX24-D1-03", "SP33-002", "WXK02-008");
        
        setOriginalName("半月の巫女　タマヨリヒメ");
        setAltNames("ハンゲツノミコタマヨリヒメ Hangetsu no Miko Tamayorihime");
        
        setName("en", "Tamayorihime, Half Moon Miko");
        
        setName("en_fan", "Tamayorihime, Half Moon Miko");
        
		setName("zh_simplified", "半月的巫女 玉依姬");
        setLRIGType(CardLRIGType.TAMA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
