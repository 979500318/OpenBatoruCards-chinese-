package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_GnomePhantomApparition extends Card {
    
    public SIGNI_G2_GnomePhantomApparition()
    {
        setImageSets("WXK01-095");
        
        setOriginalName("幻怪　ノーム");
        setAltNames("ゲンカイノーム Genkai Noomu");
        
        setName("en", "Gnome, Phantom Apparition");
        
		setName("zh_simplified", "幻怪 诺姆");
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(2);
        setPower(7000);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
