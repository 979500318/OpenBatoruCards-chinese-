package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K1_YaminoNeedlePrincessOfCreationOne extends Card {
    
    public LRIG_K1_YaminoNeedlePrincessOfCreationOne()
    {
        setImageSets("WX25-P1-032");
        
        setOriginalName("創造の針姫　ヤミノ＝Ⅰ");
        setAltNames("ツムギシトビラアトノルワン Souzou no Harihime Yamino Wan");
        
        setName("en", "Yamino-I, Needle Princess of Creation");
        
		setName("zh_simplified", "创造的针姬 暗乃 =Ⅰ式");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YAMINO);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
