package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_R2_LiberatorRilMemoryOfDetermination extends Card {
    
    public LRIG_R2_LiberatorRilMemoryOfDetermination()
    {
        setImageSets("WXDi-P15-014");
        
        setOriginalName("決意の記憶　解放者リル");
        setAltNames("ケツイノキオクカイホウシャリル Ketsui no Kioku Kaihousha Riru");
        
        setName("en", "Liberator Ril, Memory of Resolve");
        
        
        setName("en_fan", "Liberator Ril, Memory of Determination");
        
		setName("zh_simplified", "决意的记忆 解放者莉露");
        setLRIGType(CardLRIGType.RIL);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
