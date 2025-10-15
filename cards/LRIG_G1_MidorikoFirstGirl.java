package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_G1_MidorikoFirstGirl extends Card {
    
    public LRIG_G1_MidorikoFirstGirl()
    {
        setImageSets(Mask.IGNORE+"WD04-004", "WXDi-P10-018", "WX24-D4-02", "SP33-024");
        
        setOriginalName("一ノ娘　緑姫");
        setAltNames("イチノムスメミドリコ Ichi no Musume Midoriko");
        
        setName("en", "Midoriko, First Girl");
        
        setName("en_fan", "Midoriko, First Girl");
        
		setName("zh_simplified", "一之娘 绿姬");
        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.KEY_CLASSIC, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
