package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_AzumiKagamihara extends Card {
    
    public LRIG_B0_AzumiKagamihara()
    {
        setImageSets("PR-282");
        
        setOriginalName("各務原あづみ");
        setAltNames("カガミハラアヅミ Kagamihara Azumi");
        
        setName("en", "Azumi Kagamihara");
        
		setName("zh_simplified", "各务原安昙");
        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
