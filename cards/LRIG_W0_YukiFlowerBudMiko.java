package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W0_YukiFlowerBudMiko extends Card {
    
    public LRIG_W0_YukiFlowerBudMiko()
    {
        setImageSets("WDK07-Y05", "SPK02-06A","SPK03-11","SPK04-11","SPK08-11","SPK09-11","SPK15-03", "SP35-011", "PR-K035");
        
        setOriginalName("花蕾の巫女　ユキ");
        setAltNames("ハナツボミノミコユキ Hanatsubomi no Miko Yuki");
        
        setName("en", "Yuki, Flower Bud Miko");
        
		setName("zh_simplified", "花蕾的巫女 雪");
        setLRIGType(CardLRIGType.IONA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
