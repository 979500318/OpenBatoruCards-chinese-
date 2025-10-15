package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_G2_MelCampari extends Card {
    
    public LRIG_G2_MelCampari()
    {
        setImageSets("WX15-019", "WXK07-016", "WXDi-P09-018");
        
        setOriginalName("メル＝カンパリ");
        setAltNames("メルカンパリ Meru Kanpari");
        
        setName("en", "Mel - Campari");
        
        setName("en_fan", "Mel-Campari");
        
		setName("zh_simplified", "梅露=金巴利");
        setLRIGType(CardLRIGType.MEL);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
