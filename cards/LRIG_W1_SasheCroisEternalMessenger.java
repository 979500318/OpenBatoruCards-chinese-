package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W1_SasheCroisEternalMessenger extends Card {
    
    public LRIG_W1_SasheCroisEternalMessenger()
    {
        setImageSets("WD09-004", "SP33-009", "WXDi-P11-012","WX25-P2-012");
        
        setOriginalName("悠久の使者　サシェ・クロワス");
        setAltNames("ユウキュウノシシャサシェクロワス Yuukyuu no Shisha Sashe Kurowasu");
        
        setName("en", "Sashe Croix, Eternal Emissary");
        
        setName("en_fan", "Sashe Crois, Eternal Messenger");
        
		setName("zh_simplified", "悠久的使者 莎榭·三日月");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SASHE);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
