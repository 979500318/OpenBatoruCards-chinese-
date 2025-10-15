package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R1_YuzukiOneFlamingYell extends Card {
    
    public LRIG_R1_YuzukiOneFlamingYell()
    {
        setImageSets("WXDi-P06-015");
        
        setOriginalName("炎唱　遊月・壱");
        setAltNames("エンショウユヅキイチ Enshou Yuzuki Ichi");
        
        setName("en", "Yuzuki One, Blazing Chant");
        
        setName("en_fan", "Yuzuki One, Flaming Yell");
        
		setName("zh_simplified", "炎唱 游月·壹");
        setLRIGType(CardLRIGType.YUZUKI);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
