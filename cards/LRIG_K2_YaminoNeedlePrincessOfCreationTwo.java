package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_K2_YaminoNeedlePrincessOfCreationTwo extends Card {
    
    public LRIG_K2_YaminoNeedlePrincessOfCreationTwo()
    {
        setImageSets("WX25-P1-033");
        
        setOriginalName("創造の針姫　ヤミノ＝Ⅲ");
        setAltNames("ツムギシトビラアトノルツー Souzou no Harihime Yamino Tsuu");
        
        setName("en", "Yamino-II, Needle Princess of Creation");
        
		setName("zh_simplified", "创造的针姬 暗乃 = Ⅱ式");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YAMINO);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
