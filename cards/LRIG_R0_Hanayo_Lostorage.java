package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R0_Hanayo_Lostorage extends Card {
    
    public LRIG_R0_Hanayo_Lostorage()
    {
        setImageSets("SPK22-04");
        
        setOriginalName("華代");
        setAltNames("Hanayo");
        
        setName("en", "Hanayo");
        
		setName("zh_simplified", "华代");
        setLRIGType(CardLRIGType.HANAYO);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
