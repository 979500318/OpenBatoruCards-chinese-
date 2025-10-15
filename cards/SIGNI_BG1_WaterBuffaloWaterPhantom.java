package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_BG1_WaterBuffaloWaterPhantom extends Card {
    
    public SIGNI_BG1_WaterBuffaloWaterPhantom()
    {
        setImageSets("WXDi-D01-018", "SPDi38-38");
        
        setOriginalName("幻水　スイギュウ");
        setAltNames("ゲンスイギュウ 	Gensui Suigyuu");
        
        setName("en", "Water Buffalo, Phantom Aquatic Beast");
        
        setName("en_fan", "Water Buffalo, Water Phantom");
        
		setName("zh_simplified", "幻水 水牛牛");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
