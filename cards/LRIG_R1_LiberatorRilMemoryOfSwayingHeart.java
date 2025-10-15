package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R1_LiberatorRilMemoryOfSwayingHeart extends Card {
    
    public LRIG_R1_LiberatorRilMemoryOfSwayingHeart()
    {
        setImageSets("WXDi-P15-013");
        
        setOriginalName("揺心の記憶　解放者リル");
        setAltNames("ヨウシンノキオクカイホウシャリル Youshin no Kioku Kaihousha Riru");
        
        setName("en", "Liberator Ril, Memory of Uncertainty");
        
        
        setName("en_fan", "Liberator Ril, Memory of Swaying Heart");
        
		setName("zh_simplified", "摇心的记忆 解放者莉露");
        setLRIGType(CardLRIGType.RIL);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
