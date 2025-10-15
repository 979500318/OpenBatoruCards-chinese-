package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K0_YSMarineHatch extends Card {
    
    public LRIG_K0_YSMarineHatch()
    {
        setImageSets("PR-K062");
        
        setOriginalName("ＹＳマリン＝ハッチ");
        setAltNames("ワイエスマリンハッチ Wai Esu Marin Hatchi");
        
        setName("en", "YS Marine-Hatch");
        
		setName("zh_simplified", "YS马琳=孵化");
        setLRIGType(CardLRIGType.MYU);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
