package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W0_RememberStarReadingMiko extends Card {
    
    public LRIG_W0_RememberStarReadingMiko()
    {
        setImageSets("PR-050", Mask.IGNORE+"PR-134","PR-196");
        
        setOriginalName("星占の巫女　リメンバ");
        setAltNames("センセイノミコリメンバ Sensei no Miko Rimenba");
        
        setName("en", "Remember, Star-Reading Miko");
        
		setName("zh_simplified", "星占的巫女 忆");
        setLRIGType(CardLRIGType.REMEMBER);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
