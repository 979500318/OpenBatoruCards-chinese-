package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RK1_EckesachsSmallEquipment extends Card {
    
    public SIGNI_RK1_EckesachsSmallEquipment()
    {
        setImageSets("WXDi-D07-020");
        
        setOriginalName("小装　エケザクス");
        setAltNames("ショウソウエケザクス Shousou Ekezakusu");
        
        setName("en", "Eckesachs, Lightly Armed");
        
        setName("en_fan", "Eckesachs, Small Equipment");
        
		setName("zh_simplified", "小装 厄齐赞古斯");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(1);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
