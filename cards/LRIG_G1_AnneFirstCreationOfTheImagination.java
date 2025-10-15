package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G1_AnneFirstCreationOfTheImagination extends Card {
    
    public LRIG_G1_AnneFirstCreationOfTheImagination()
    {
        setImageSets("SP07-004", "WX04-019", "SP33-026", "WXDi-P08-017", "WX24-P3-024");
        
        setOriginalName("想像の創造　アン＝ファースト");
        setAltNames("ソウゾウノソウゾウアンファースト Souzou no Souzou An Fasuto");
        
        setName("en", "Ann I, Icon of Innovation");
        
        setName("en_fan", "Anne-First, Creation of the Imagination");
        
		setName("zh_simplified", "想象的创造 安=FIRST");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
