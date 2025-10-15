package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K0_Deneblog extends Card {
    
    public LRIG_K0_Deneblog()
    {
        setImageSets("WXDi-P13-091");
        
        setOriginalName("デネブログ");
        setAltNames("Deneburogu");
        
        setName("en", "Deneblog");
        
        
        setName("en_fan", "Deneblog");
        
		setName("zh_simplified", "DM博客娘");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MYU);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
