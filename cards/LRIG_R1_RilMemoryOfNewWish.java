package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R1_RilMemoryOfNewWish extends Card {
    
    public LRIG_R1_RilMemoryOfNewWish()
    {
        setImageSets("WXDi-P07-014");
        
        setOriginalName("求新の記憶　リル");
        setAltNames("キュウシンノキオクリル Kyuushin no Kioku Riru");
        
        setName("en", "Ril, Memory of Seeking Change");
        
        setName("en_fan", "Ril, Memory of New Wish");
        
		setName("zh_simplified", "求新的记忆 莉露");
        setLRIGType(CardLRIGType.RIL);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
