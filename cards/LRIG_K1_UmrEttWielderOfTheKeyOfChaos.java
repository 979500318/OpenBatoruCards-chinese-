package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K1_UmrEttWielderOfTheKeyOfChaos extends Card {
    
    public LRIG_K1_UmrEttWielderOfTheKeyOfChaos()
    {
        setImageSets("WD08-004", "WXDi-P16-030","WX25-P1-028","SPDi44-14", "SP33-033");
        
        setOriginalName("混沌の鍵主　ウムル＝エット");
        setAltNames("コントンノカギヌシウムルエット Konton no Kaginushi Umuru Etto");
        
        setName("en", "Umr =Ett=, Key to Chaos");
        
        
        setName("en_fan", "Umr-Ett, Wielder of the Key of Chaos");
        
		setName("zh_simplified", "混沌的键主 乌姆尔=ETT");
        setLRIGType(CardLRIGType.UMR);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
