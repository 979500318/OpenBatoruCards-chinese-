package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_Gorgeous extends Card {
    
    public LRIG_B0_Gorgeous()
    {
        setImageSets("PR-K009");
        
        setOriginalName("ゴー☆ジャス");
        setAltNames("Goojasu Gorgeous");
        
        setName("en", "Gor☆geous");
        
		setName("zh_simplified", "华☆丽");
        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
