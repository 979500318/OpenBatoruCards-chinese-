package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardSIGNIClass;
import open.batoru.data.CardConst.CardType;

public final class SIGNI_X1_ServantZERO extends Card {
    
    public SIGNI_X1_ServantZERO()
    {
        setImageSets("WX17_TK-01", "WXDi-P07-TK01-A");
        
        setOriginalName("サーバント　ZERO");
        setAltNames("サーバントゼロ Saabanto Zero");
        
        setName("en", "Servant ZERO");
        
        
        setName("en_fan", "Servant ZERO");
        
		setName("zh_simplified", "鬼牌");
        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClass.ORIGIN);
        setLevel(1);
        setPower(1000);
    }
}
