package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R0_HanayoZero_Lostorage extends Card {
    
    public LRIG_R0_HanayoZero_Lostorage()
    {
        setImageSets("WXK07-010", "SPK03-15","SPK04-15","SPK16-3A","SPK18-05","SPK19-01");
        
        setOriginalName("華代・零");
        setAltNames("ハナヨゼロ Hanayo Zero");
        
        setName("en", "Hanayo-Zero");
        
		setName("zh_simplified", "华代·零");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
