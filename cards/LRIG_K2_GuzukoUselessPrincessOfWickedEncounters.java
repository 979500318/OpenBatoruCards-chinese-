package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_K2_GuzukoUselessPrincessOfWickedEncounters extends Card {
    
    public LRIG_K2_GuzukoUselessPrincessOfWickedEncounters()
    {
        setImageSets("WD22-009-G", "WXK01-030", "WXDi-P09-026");
        
        setOriginalName("逢魔の駄姫　グズ子");
        setAltNames("オウマのダキグズコ Ouma no Daki Guzuko");
        
        setName("en", "Guzuko, Worthless Queen of Twilight");
        
        setName("en_fan", "Guzuko, Useless Princess of Wicked Encounters");
        
		setName("zh_simplified", "逢魔的驮姬 迟钝子");
        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.KEY_CLASSIC, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
