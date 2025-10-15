package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B1_AllosPirulukDecilitre extends Card {
    
    public LRIG_B1_AllosPirulukDecilitre()
    {
        setImageSets("WXDi-P14-014");
        
        setOriginalName("アロス・ピルルク　dℓ");
        setAltNames("アロスピルルクデシリットル Arosu Piruruku Deshirittoru dl");
        
        setName("en", "Allos Piruluk dℓ");
        
        setName("en_fan", "Allos Piruluk Decilitre");
        
		setName("zh_simplified", "阿洛斯·皮璐璐可　dl");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
