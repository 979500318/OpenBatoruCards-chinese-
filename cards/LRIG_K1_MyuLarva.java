package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K1_MyuLarva extends Card {
    
    public LRIG_K1_MyuLarva()
    {
        setImageSets("WD11-004", "SP33-035", "WXDi-P11-024","WX25-P2-028");
        
        setOriginalName("ミュウ＝ラーバ");
        setAltNames("ミュウラーバ Myuu Raaba");
        
        setName("en", "Myu=Lava");
        
        setName("en_fan", "Myu-Larva");
        
		setName("zh_simplified", "缪＝幼虫");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MYU);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
