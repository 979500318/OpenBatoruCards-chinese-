package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B3_UndineWaterSpiritOfHell extends Card {
    
    public SIGNI_B3_UndineWaterSpiritOfHell()
    {
        setImageSets("WXK01-083");
        
        setOriginalName("魔界の水霊　ウンディーネ");
        setAltNames("マカイノスイレイウンディーネ Makai no Suirei Undiine");
        
        setName("en", "Undine, Water Spirit of Hell");
        
		setName("zh_simplified", "魔界的水灵 温蒂妮");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
