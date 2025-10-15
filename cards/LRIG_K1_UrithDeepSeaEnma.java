package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K1_UrithDeepSeaEnma extends Card {
    
    public LRIG_K1_UrithDeepSeaEnma()
    {
        setImageSets("WXDi-P14-020");
        
        setOriginalName("深海の閻魔　ウリス");
        setAltNames("シンカイノエンマウリス Shinkai no Enma Urisu");
        
        setName("en", "Urith, Abyssal Enma");
        
        
        setName("en_fan", "Urith, Deep Sea Enma");
        
		setName("zh_simplified", "深海的阎魔 乌莉丝");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.URITH);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
