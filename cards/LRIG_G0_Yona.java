package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G0_Yona extends Card {
    
    public LRIG_G0_Yona()
    {
        setImageSets("PR-448");
        
        setOriginalName("ヨナ");
        setAltNames("Yona");
        
        setName("en", "Yona");
        
		setName("zh_simplified", "尤娜");
        setLRIGType(CardLRIGType.ANN);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
