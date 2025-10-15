package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G4_MinotaurPhantomApparition extends Card {
    
    public SIGNI_G4_MinotaurPhantomApparition()
    {
        setImageSets("WXK01-091");
        
        setOriginalName("幻怪　ミノタウロス");
        setAltNames("ゲンカイミノタウロス Genkai Minotaurosu");
        
        setName("en", "Minotaur, Phantom Apparition");
        
		setName("zh_simplified", "幻怪 米诺陶洛斯");
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(4);
        setPower(15000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
