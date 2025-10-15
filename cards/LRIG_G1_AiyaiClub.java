package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G1_AiyaiClub extends Card {
    
    public LRIG_G1_AiyaiClub()
    {
        setImageSets("WD12-004", "SP33-028", "WXDi-P11-018","WX25-P2-024");
        
        setOriginalName("アイヤイ★クラブ");
        setAltNames("アイヤイクラブ Aiyai Kurabu");
        
        setName("en", "Aiyai ★ Club");
        
        setName("en_fan", "Aiyai★Club");
        
		setName("zh_simplified", "艾娅伊★梅花");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AIYAI);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
