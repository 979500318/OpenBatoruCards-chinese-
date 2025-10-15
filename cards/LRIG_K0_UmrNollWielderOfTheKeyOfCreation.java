package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K0_UmrNollWielderOfTheKeyOfCreation extends Card {
    
    public LRIG_K0_UmrNollWielderOfTheKeyOfCreation()
    {
        setImageSets("WD08-005", "WXDi-P16-029","WX25-P1-027","SPDi44-13", "SP16-005","SP18-006","SP34-014","SP35-024", "SPK05-09","SPK05-10", "PR-065",Mask.IGNORE+"PR-133");
        
        setOriginalName("創造の鍵主　ウムル＝ノル");
        setAltNames("ソウゾウノカギヌシウムルノル Soozoo no Kaginushi Umuru Noru");
        
        setName("en", "Umr =Noll=, Key to Creation");
        
        
        setName("en_fan", "Umr-Noll, Wielder of the Key of Creation");
        
		setName("zh_simplified", "创造的键主 乌姆尔=NOLL");
        setLRIGType(CardLRIGType.UMR);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
