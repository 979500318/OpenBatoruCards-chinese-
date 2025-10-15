package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_AllosPirulukMillilitre extends Card {
    
    public LRIG_B0_AllosPirulukMillilitre()
    {
        setImageSets("WXDi-P14-013", "SPDi28-03");
        
        setOriginalName("アロス・ピルルク　mℓ");
        setAltNames("アロスピルルクミリリットル Arosu Piruruku Miririttoru ml");
        
        setName("en", "Allos Piruluk mℓ");
        
        
        setName("en_fan", "Allos Piruluk Millilitre");
        
		setName("zh_simplified", "阿洛斯·皮璐璐可　ml");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
