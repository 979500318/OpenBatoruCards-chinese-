package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W0_YukiMikoOfFallingInLove extends Card {
    
    public LRIG_W0_YukiMikoOfFallingInLove()
    {
        setImageSets("PR-241", "SP13-002A","SP13-002B","SP18-002");
        
        setOriginalName("恋慕の巫女　ユキ");
        setAltNames("レンボノミコユキ Renbo no Miko Yuki");
        
        setName("en", "Yuki, Miko of Falling in Love");
        
		setName("zh_simplified", "恋慕的巫女 雪");
        setLRIGType(CardLRIGType.IONA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
