package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K1_ShunSunoharaMeihuaYuansTeachings extends Card {
    
    public LRIG_K1_ShunSunoharaMeihuaYuansTeachings()
    {
        setImageSets("WX25-CP1-022");
        
        setOriginalName("春原シュン[梅花園の教育]");
        setAltNames("スノハラシュンバイカエンノキョウイク Sunohara Shun Baikaen no Kyouiku");
        
        setName("en", "Sunohara Shun [Meihua Yuan's Teachings]");
        
        setName("en_fan", "Shun Sunohara [Meihua Yuan's Teachings]");
        
		setName("zh_simplified", "春原瞬[梅花园的教育]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SHUN);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
