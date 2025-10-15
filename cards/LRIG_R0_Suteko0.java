package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R0_Suteko0 extends Card {
    
    public LRIG_R0_Suteko0()
    {
        setImageSets("PR-379");
        
        setOriginalName("ステコ　―０―");
        setAltNames("ステコゼロ Suteko Zero");
        
        setName("en", "Suteko -0-");
        
		setName("zh_simplified", "寿子 -0-");
        setLRIGType(CardLRIGType.CARNIVAL);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCoins(+3);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
