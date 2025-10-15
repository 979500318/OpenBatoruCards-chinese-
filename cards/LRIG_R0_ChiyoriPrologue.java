package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R0_ChiyoriPrologue extends Card {
    
    public LRIG_R0_ChiyoriPrologue()
    {
        setImageSets("WX24-P3-015");
        
        setOriginalName("ちより　序章");
        setAltNames("チヨリジョショウ Chiyori Joshou");
        
        setName("en", "Chiyori, Prologue");
        
		setName("zh_simplified", "千依 序章");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.CHIYORI);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
