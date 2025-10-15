package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_UmrNollWielderOfTheKeyOfPerformingSalvation extends Card {
    
    public LRIG_B0_UmrNollWielderOfTheKeyOfPerformingSalvation()
    {
        setImageSets("WXDi-D01-008", "SPDi01-03","SPDi11-05","SPDi23-03","SPDi24-04", "PR-Di003");
        
        setOriginalName("奏救の鍵主　ウムル＝ノル");
        setAltNames("ソウキュウノカギヌシウムルノル Sookyuu no Kaginushi Umuru Noru");
        
        setName("en", "Umr =Noll=, Key to Salvation");
        
        setName("en_fan", "Umr-Noll, Wielder of the Key of Performing Salvation");
        
		setName("zh_simplified", "奏救的键主 乌姆尔=NOLL");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.UMR);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
