package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W0_TamayorihimeNewMoonMiko extends Card {
    
    public LRIG_W0_TamayorihimeNewMoonMiko()
    {
        setImageSets(Mask.IGNORE+"WD01-005", "WDK05-T05", "WXDi-D08-001", "WX24-D1-01",
                Mask.IGNORE+"SP04-001","SP05-001","SP13-001A","SP13-001B","SP18-001","SP34-009","SP35-005",
                Mask.IGNORE+"PR-009","PR-013",Mask.IGNORE+"PR-123","PR-137",Mask.IGNORE+"PR-158",Mask.IGNORE+"PR-225","PR-268",
                "SPK02-07B","SPK03-05","SPK04-05","SPK05-01","SPK05-02","SPK07-05","SPK08-05","SPK09-05","SPK14-01", "PR-K027",
                "SPDi01-31","SPDi13-03","SPDi14-01","SPDi15-01","SPDi21-01","SPDi23-11","SPDi24-01", "PR-Di014"
        );
        
        setOriginalName("新月の巫女　タマヨリヒメ");
        setAltNames("シンゲツノミコタマヨリヒメ Shingetsu no Miko Tamayorihime");
        
        setName("en", "Tamayorihime, New Moon Miko");
        
        setName("en_fan", "Tamayorihime, New Moon Miko");
        
		setName("zh_simplified", "新月的巫女 玉依姬");
        setLRIGType(CardLRIGType.TAMA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
