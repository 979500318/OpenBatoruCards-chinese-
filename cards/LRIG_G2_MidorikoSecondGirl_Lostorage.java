package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_G2_MidorikoSecondGirl_Lostorage extends Card {
    
    public LRIG_G2_MidorikoSecondGirl_Lostorage()
    {
        setImageSets("WXK01-023");
        
        setOriginalName("二ノ娘　翠子");
        setAltNames("ニノムスメミドリコ Ni no Musume Midoriko");
        
        setName("en", "Midoriko, Second Girl");
        
		setName("zh_simplified", "二娘 翠子");
        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
