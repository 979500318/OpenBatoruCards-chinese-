package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K0_GuzukoUselessPrincess extends Card {
    
    public LRIG_K0_GuzukoUselessPrincess()
    {
        setImageSets("SP29-009", "WDK04-005", "SP20-012","SP30-009","SP34-016","SP35-004", Mask.IGNORE+"PR-385","PR-399","PR-403",
                     "SPK02-07A","SPK03-04","SPK04-04","SPK07-04","SPK08-04","SPK09-04", Mask.IGNORE+"PR-K016", "SPK14-05","SPK16-1C", "WXDi-P09-024", "SPDi01-51");
        
        setOriginalName("駄姫　グズ子");
        setAltNames("ダキグズコ Daki Guzuko");
        
        setName("en", "Guzuko, Worthless Queen");
        
        setName("en_fan", "Guzuko, Useless Princess");
        
		setName("zh_simplified", "驮姬 迟钝子");
        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setCoins(+3);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
