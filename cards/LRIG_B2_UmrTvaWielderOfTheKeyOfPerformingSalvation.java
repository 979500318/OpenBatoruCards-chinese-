package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_B2_UmrTvaWielderOfTheKeyOfPerformingSalvation extends Card {
    
    public LRIG_B2_UmrTvaWielderOfTheKeyOfPerformingSalvation()
    {
        setImageSets("WXDi-P00-014");
        
        setOriginalName("奏救の鍵主　ウムル＝トヴォ");
        setAltNames("ソウキュウノカギヌシウムルトヴォ Sookyuu no Kaginushi Umuru Tovo");
        
        setName("en", "Umr =Två=, Key to Salvation");
        
        setName("en_fan", "Umr-Två, Wielder of the Key of Performing Salvation");
        
		setName("zh_simplified", "奏救的键主 乌姆尔=TVA");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.UMR);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
