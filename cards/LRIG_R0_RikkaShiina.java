package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R0_RikkaShiina extends Card {
    
    public LRIG_R0_RikkaShiina()
    {
        setImageSets("PR-438");
        
        setOriginalName("椎名　六花");
        setAltNames("シイナリッカ Shiina Rikka");
        
        setName("en", "Rikka Shiina");
        
		setName("zh_simplified", "椎名 六花");
        setLRIGType(CardLRIGType.HANAYO);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
