package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B1_CodePirulukKilo extends Card {
    
    public LRIG_B1_CodePirulukKilo()
    {
        setImageSets(Mask.IGNORE+"WD03-004", "WD16-004", "WXDi-P06-018", "WX24-D3-02", "SP19-003","SP33-017");
        
        setOriginalName("コード・ピルルク・K");
        setAltNames("コードピルルクキロ Koodo Piruruku Kiro Kilo");
        
        setName("en", "Code Piruluk K");
        
        setName("en_fan", "Code Piruluk K");
        
		setName("zh_simplified", "代号·皮璐璐可·K");
        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
