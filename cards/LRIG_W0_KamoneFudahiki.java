package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W0_KamoneFudahiki extends Card {
    
    public LRIG_W0_KamoneFudahiki()
    {
        setImageSets("PR-150");
        
        setOriginalName("札引かもね");
        setAltNames("フダヒキカモネ Fudahiki Kamone");
        
        setName("en", "Kamone Fudahiki");
        
		setName("zh_simplified", "札引歌百音");
        setLRIGType(CardLRIGType.TAMA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
