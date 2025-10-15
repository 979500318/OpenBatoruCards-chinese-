package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RK2_SansetsukonMediumEquipment extends Card {
    
    public SIGNI_RK2_SansetsukonMediumEquipment()
    {
        setImageSets("WXDi-P04-090");
        
        setOriginalName("小装　サンセツコン");
        setAltNames("ショウソウサンセツコン Shousou Sansetsukon");
        
        setName("en", "Sansetsukon, High Armed");
        
        setName("en_fan", "Sansetsukon, Medium Equipment");
        
		setName("zh_simplified", "中装 三节棍");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
