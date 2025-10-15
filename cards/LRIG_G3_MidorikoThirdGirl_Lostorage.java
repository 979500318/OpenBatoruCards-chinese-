package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_G3_MidorikoThirdGirl_Lostorage extends Card {
    
    public LRIG_G3_MidorikoThirdGirl_Lostorage()
    {
        setImageSets("WXK01-022");
        
        setOriginalName("三ノ娘　翠子");
        setAltNames("サンノムスメミドリコ San no Musume Midoriko");
        
        setName("en", "Midoriko, Third Girl");
        
		setName("zh_simplified", "三娘 翠子");
        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(8);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
