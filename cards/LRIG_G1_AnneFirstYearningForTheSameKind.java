package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G1_AnneFirstYearningForTheSameKind extends Card {
    
    public LRIG_G1_AnneFirstYearningForTheSameKind()
    {
        setImageSets("WXDi-P14-017");
        
        setOriginalName("同系の憧憬　アン＝ファースト");
        setAltNames("ドウケイノドウケイアンファースト Doukei no Doukei An Faasuto");
        
        setName("en", "Ann I, Longing Likeness");
        
        
        setName("en_fan", "Anne-First, Yearning for the Same Kind");
        
		setName("zh_simplified", "同系的憧憬 安=FIRST");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
