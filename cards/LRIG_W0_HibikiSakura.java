package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W0_HibikiSakura extends Card {
    
    public LRIG_W0_HibikiSakura()
    {
        setImageSets("PR-K057");
        
        setOriginalName("紗倉ひびき");
        setAltNames("サクラヒビキ Sakura Hibiki");
        
        setName("en", "Hibiki Sakura");
        
		setName("zh_simplified", "纱仓响");
        setLRIGType(CardLRIGType.TAMA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
