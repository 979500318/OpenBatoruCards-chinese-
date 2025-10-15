package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K0_HinaSorasaki extends Card {
    
    public LRIG_K0_HinaSorasaki()
    {
        setImageSets("WXDi-CP02-020", "SPDi31-04","SPDi32-04");
        
        setOriginalName("空崎ヒナ");
        setAltNames("ソラサキヒナ Sorasaki Hina");
        
        setName("en", "Sorasaki Hina");
        
        
        setName("en_fan", "Hina Sorasaki");
        
		setName("zh_simplified", "空崎日奈");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HINA);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

