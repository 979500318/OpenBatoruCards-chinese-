package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G0_Midoriko_Lostorage extends Card {
    
    public LRIG_G0_Midoriko_Lostorage()
    {
        setImageSets("PR-K003");
        
        setOriginalName("翠子");
        setAltNames("ミドリコ Midoriko");
        
        setName("en", "Midoriko");
        
		setName("zh_simplified", "翠子");
        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
