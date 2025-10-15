package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_R2_YuzukiTwoFlamingSwimmer extends Card {
    
    public LRIG_R2_YuzukiTwoFlamingSwimmer()
    {
        setImageSets("WXDi-P14-012");
        
        setOriginalName("炎泳　遊月・濡");
        setAltNames("エンエイユヅキニ Enei Yuzuki Ni");
        
        setName("en", "Yuzuki Soaker, Surflame");
        
        
        setName("en_fan", "Yuzuki Two, Flaming Swimmer");
        
		setName("zh_simplified", "炎泳  游月·濡");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUZUKI);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
