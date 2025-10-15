package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RB1_SeaStarWaterPhantom extends Card {
    
    public SIGNI_RB1_SeaStarWaterPhantom()
    {
        setImageSets("WXDi-P06-086");
        
        setOriginalName("幻水　シースター");
        setAltNames("ゲンスイシースター Gensui Shii Sutaa");
        
        setName("en", "Sea Star, Phantom Aquatic Beast");
        
        setName("en_fan", "Sea Star, Water Phantom");
        
		setName("zh_simplified", "幻水 海星星");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
