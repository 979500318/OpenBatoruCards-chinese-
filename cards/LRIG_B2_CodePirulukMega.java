package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B2_CodePirulukMega extends Card {
    
    public LRIG_B2_CodePirulukMega()
    {
        setImageSets(Mask.IGNORE+"WD03-003", "WD16-003", "WXDi-P06-019","WX24-D3-03", "SP33-016");
        
        setOriginalName("コード・ピルルク・M");
        setAltNames("コードピルルクメガ Koodo Piruruku Mega");
        
        setName("en", "Code Piruluk M");
        
        setName("en_fan", "Code Piruluk M");
        
		setName("zh_simplified", "代号·皮璐璐可·M");
        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
