package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_K2_HinaSorasakiReloadAndDestroy extends Card {
    
    public LRIG_K2_HinaSorasakiReloadAndDestroy()
    {
        setImageSets("WXDi-CP02-022");
        
        setOriginalName("空崎ヒナ[リロードアンドデストロイ]");
        setAltNames("ソラサキヒナリロードアンドデストロイ Sorasaki Hina Riroodo Ando Desutoroi");
        
        setName("en", "Sorasaki Hina [Lock & Load]");
        
        
        setName("en_fan", "Hina Sorasaki [Reload and Destroy]");
        
		setName("zh_simplified", "空崎日奈[装填与毁灭]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HINA);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

