package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_BG2_TreeRibbityWaterPhantom extends Card {
    
    public SIGNI_BG2_TreeRibbityWaterPhantom()
    {
        setImageSets("WXDi-P00-088");
        
        setOriginalName("幻水　アマケロン");
        setAltNames("ゲンスイアマケロン Gensui Amakeron");
        
        setName("en", "Tree Froggy, Phantom Aquatic Beast");
        
        setName("en_fan", "Tree Ribbity, Water Phantom");
        
		setName("zh_simplified", "幻水 雨蛙蛙");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
