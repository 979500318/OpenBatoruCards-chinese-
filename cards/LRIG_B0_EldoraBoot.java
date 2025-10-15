package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_EldoraBoot extends Card {
    
    public LRIG_B0_EldoraBoot()
    {
        setImageSets("WDK07-E05", "SPK02-06C","SPK03-10","SPK04-10","SPK08-10","SPK09-10","SPK15-02", "SP35-010", "PR-K034");
        
        setOriginalName("エルドラ　Ｂｏｏｔ");
        setAltNames("エルドラブート Erudora Buuto");
        
        setName("en", "Eldora Boot");
        
		setName("zh_simplified", "艾尔德拉 Boot");
        setLRIGType(CardLRIGType.ELDORA);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
