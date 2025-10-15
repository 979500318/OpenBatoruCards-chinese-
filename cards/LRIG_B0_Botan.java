package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_Botan extends Card {
    
    public LRIG_B0_Botan()
    {
        setImageSets("PR-172");
        
        setOriginalName("ぼたん");
        setAltNames("ボタン Botan");
        
        setName("en", "Botan");
        
		setName("zh_simplified", "佩奥妮");
        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
