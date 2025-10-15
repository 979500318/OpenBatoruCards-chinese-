package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W0_Suteko extends Card {
    
    public LRIG_W0_Suteko()
    {
        setImageSets("PR-103");
        
        setOriginalName("ステコ");
        setAltNames("Suteko");
        
        setName("en", "Suteko");
        
		setName("zh_simplified", "寿子");
        setLRIGType(CardLRIGType.TAMA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
