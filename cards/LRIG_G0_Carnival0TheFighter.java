package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G0_Carnival0TheFighter extends Card {
    
    public LRIG_G0_Carnival0TheFighter()
    {
        setImageSets("WXDi-P15-018");
        
        setOriginalName("闘争者カーニバル　#０#");
        setAltNames("トウソウシャカーニバルワイルドゼロ Tousousha Kaanibaru Wairudo Zero Wild 0 Wild Zero Carnival 0");
        
        setName("en", "Warrior Carnival #0#");
        
        
        setName("en_fan", "Carnival #0#, The Fighter");
        
		setName("zh_simplified", "斗争者嘉年华 #0#");
        setLRIGType(CardLRIGType.CARNIVAL);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCoins(+3);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
