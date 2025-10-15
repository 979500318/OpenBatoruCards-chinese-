package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.Cost;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K2_UmrTvaWielderOfTheKeyOfChaos extends Card {
    
    public LRIG_K2_UmrTvaWielderOfTheKeyOfChaos()
    {
        setImageSets("WD08-003", "WXDi-P16-031","WX25-P1-029","SPDi44-15", "SP33-032");
        
        setOriginalName("混沌の鍵主　ウムル＝トヴォ");
        setAltNames("コントンノカギヌシウムルトヴォ Konton no Kaginushi Umuru Tovuo");
        
        setName("en", "Umr =Tv\u00e5=, Key to Chaos");
        
        
        setName("en_fan", "Umr-Två, Wielder of the Key of Chaos");
        
		setName("zh_simplified", "混沌的键主 乌姆尔=TVA");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.UMR);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
