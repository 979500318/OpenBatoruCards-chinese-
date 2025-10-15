package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K0_HanareDarkOne extends Card {
    
    public LRIG_K0_HanareDarkOne()
    {
        setImageSets("PR-304", "SP35-027", "PR-279", "WXDi-P10-020","SPDi01-58");
        
        setOriginalName("冥者　ハナレ");
        setAltNames("メイジャハナレ Meija Hanare");
        
        setName("en", "Hanare, Mourner");
        
        setName("en_fan", "Hanare, Dark One");
        
		setName("zh_simplified", "冥者 离");
        setLRIGType(CardLRIGType.HANARE);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
