package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_KAGEHA extends Card {
    
    public LRIG_B0_KAGEHA()
    {
        setImageSets("PR-401");
        
        setOriginalName("ＫＡＧＥＨＡ");
        setAltNames("カゲハ Kageha");
        
        setName("en", "KAGEHA");
        
		setName("zh_simplified", "KAGEHA");
        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
