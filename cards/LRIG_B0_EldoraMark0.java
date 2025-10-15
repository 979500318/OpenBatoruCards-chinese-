package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_EldoraMark0 extends Card {
    
    public LRIG_B0_EldoraMark0()
    {
        setImageSets("WD06-005", "SP05-006","SP34-012", "PR-060");
        
        setOriginalName("エルドラ＝マーク０");
        setAltNames("エルドラマークゼロ Erudora Maaku Zero Eldora Mark Zero");
        
        setName("en", "Eldora-Mark 0");
        
		setName("zh_simplified", "艾尔德拉＝0式");
        setLRIGType(CardLRIGType.ELDORA);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
