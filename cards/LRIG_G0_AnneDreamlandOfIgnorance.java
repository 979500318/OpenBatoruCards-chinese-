package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G0_AnneDreamlandOfIgnorance extends Card {
    
    public LRIG_G0_AnneDreamlandOfIgnorance()
    {
        setImageSets("WXDi-P14-016", "SPDi28-04");
        
        setOriginalName("無知の夢地　アン");
        setAltNames("ムチノムチアン Muchi no Muchi An");
        
        setName("en", "Ann, Witless Wonderland");
        
        
        setName("en_fan", "Anne, Dreamland of Ignorance");
        
		setName("zh_simplified", "无知的梦地 安");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
