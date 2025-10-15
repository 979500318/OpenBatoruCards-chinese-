package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R0_SutekoZero extends Card {
    
    public LRIG_R0_SutekoZero()
    {
        setImageSets("PR-306");
        
        setOriginalName("ステコ・零");
        setAltNames("ステコゼロ Suteko Zero");
        
        setName("en", "Suteko-Zero");
        
		setName("zh_simplified", "寿子·零");
        setLRIGType(CardLRIGType.YUZUKI);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
