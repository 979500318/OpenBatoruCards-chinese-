package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B1_UmrEttWielderOfTheKeyOfPerformingSalvation extends Card {
    
    public LRIG_B1_UmrEttWielderOfTheKeyOfPerformingSalvation()
    {
        setImageSets("WXDi-P00-013");
        
        setOriginalName("奏救の鍵主　ウムル＝エット");
        setAltNames("ソウキュウノカギヌシウムルエット Sookyuu no Kaginushi Umuru Etto");
        
        setName("en", "Umr =Ett=, Key to Salvation");
        
        setName("en_fan", "Umr-Ett, Wielder of the Key of Performing Salvation");
        
		setName("zh_simplified", "奏救的键主 乌姆尔=ETT");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.UMR);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
