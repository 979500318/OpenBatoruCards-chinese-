package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_K2_UrithWaterSprayingEnma extends Card {
    
    public LRIG_K2_UrithWaterSprayingEnma()
    {
        setImageSets("WXDi-P14-021");
        
        setOriginalName("散水の閻魔　ウリス");
        setAltNames("サンスイノエンマウリス Sansui no Enma Urisu");
        
        setName("en", "Urith, Splashing Enma");
        
        
        setName("en_fan", "Urith, Water-Spraying Enma");
        
		setName("zh_simplified", "散水的阎魔 乌莉丝");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.URITH);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
