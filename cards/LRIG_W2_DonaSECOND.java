package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_W2_DonaSECOND extends Card {
    
    public LRIG_W2_DonaSECOND()
    {
        setImageSets("WX16-008", "WXDi-P07-018");
        
        setOriginalName("ドーナ　ＳＥＣＯＮＤ");
        setAltNames("ドーナセカンド Doona Sekando");
        
        setName("en", "Dona SECOND");
        
        setName("en_fan", "Dona SECOND");
        
		setName("zh_simplified", "多娜 SECOND");
        setLRIGType(CardLRIGType.DONA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
