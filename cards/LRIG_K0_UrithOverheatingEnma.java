package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K0_UrithOverheatingEnma extends Card {
    
    public LRIG_K0_UrithOverheatingEnma()
    {
        setImageSets("WXDi-P14-019", "SPDi28-05");
        
        setOriginalName("過熱の閻魔　ウリス");
        setAltNames("カネツノエンマウリス Kanetsu no Enma Urisu");
        
        setName("en", "Urith, Scorching Enma");
        
        
        setName("en_fan", "Urith, Overheating Enma");
        
		setName("zh_simplified", "过热的阎魔 乌莉丝");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.URITH);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
