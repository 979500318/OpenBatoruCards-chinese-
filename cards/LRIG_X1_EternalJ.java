package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_X1_EternalJ extends Card {
    
    public LRIG_X1_EternalJ()
    {
        setImageSets("WXDi-P11-027");
        
        setOriginalName("夢限 －Ｊ－");
        setAltNames("ムゲンジョ Mugen Jo Mugen J");
        
        setName("en", "Mugen -J-");
        
        setName("en_fan", "Eternal -J-");
        
		setName("zh_simplified", "梦限 -J-");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MUGEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
