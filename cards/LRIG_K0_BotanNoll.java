package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K0_BotanNoll extends Card {
    
    public LRIG_K0_BotanNoll()
    {
        setImageSets("PR-452");
        
        setOriginalName("ぼたん＝ノル");
        setAltNames("ボタンノル Botan Noru");
        
        setName("en", "Botan-Noll");
        
		setName("zh_simplified", "佩奥妮=NOLL");
        setLRIGType(CardLRIGType.UMR);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
