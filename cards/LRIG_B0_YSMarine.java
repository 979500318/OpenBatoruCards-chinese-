package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_YSMarine extends Card {
    
    public LRIG_B0_YSMarine()
    {
        setImageSets("PR-400");
        
        setOriginalName("ＹＳマリン");
        setAltNames("ワイエスマリン Wai Esu Marin");
        
        setName("en", "YS Marine");
        
		setName("zh_simplified", "YS马琳");
        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
