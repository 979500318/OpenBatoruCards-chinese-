package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G3_CentaurPhantomApparition extends Card {
    
    public SIGNI_G3_CentaurPhantomApparition()
    {
        setImageSets("WXK01-093");
        
        setOriginalName("幻怪　ケンタウロス");
        setAltNames("ゲンカイケンタウロス Genkai Kentaurosu");
        
        setName("en", "Centaur, Phantom Apparition");
        
		setName("zh_simplified", "幻怪 肯陶罗斯");
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
