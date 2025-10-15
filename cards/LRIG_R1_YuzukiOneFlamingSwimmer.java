package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R1_YuzukiOneFlamingSwimmer extends Card {
    
    public LRIG_R1_YuzukiOneFlamingSwimmer()
    {
        setImageSets("WXDi-P14-011");
        
        setOriginalName("炎泳　遊月・衣置");
        setAltNames("エンエイユヅキイチ Enei Yuzuki Ichi");
        
        setName("en", "Yuzuki Wet, Surflame");
        
        
        setName("en_fan", "Yuzuki One, Flaming Swimmer");
        
		setName("zh_simplified", "炎泳  游月·衣置");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUZUKI);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
