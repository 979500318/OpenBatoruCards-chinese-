package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RB2_PygmySeahorseWaterPhantom extends Card {
    
    public SIGNI_RB2_PygmySeahorseWaterPhantom()
    {
        setImageSets("WXDi-P06-087");
        
        setOriginalName("幻水　ピグミーシーホース");
        setAltNames("ゲンスイピグミーシーホース Gensui Pigumii Shiihoosu");
        
        setName("en", "Bargibanti, Phantom Aquatic Beast");
        
        setName("en_fan", "Pygmy Seahorse, Water Phantom");
        
		setName("zh_simplified", "幻水 豆丁海马");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
