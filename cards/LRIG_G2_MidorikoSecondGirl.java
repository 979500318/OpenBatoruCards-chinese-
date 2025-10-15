package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.Cost;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_G2_MidorikoSecondGirl extends Card {
    
    public LRIG_G2_MidorikoSecondGirl()
    {
        setImageSets(Mask.IGNORE+"WD04-003", "WXDi-P10-019", "WX24-D4-03", "SP33-023");
        
        setOriginalName("二ノ娘　緑姫");
        setAltNames("ニノムスメミドリコ Ni no Musume Midoriko");
        
        setName("en", "Midoriko, Second Girl");
        
        setName("en_fan", "Midoriko, Second Girl");
        
		setName("zh_simplified", "二之娘 绿姬");
        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.KEY_CLASSIC, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
