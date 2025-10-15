package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R0_LiberatorRilMemoryOfInspiration extends Card {
    
    public LRIG_R0_LiberatorRilMemoryOfInspiration()
    {
        setImageSets("WXDi-P15-012");
        
        setOriginalName("鼓舞の記憶　解放者リル");
        setAltNames("コブノキオクカイホウシャリル Kobu no Kioku Kaihousha Riru");
        
        setName("en", "Liberator Ril, Memory of Inspiration");
        
        
        setName("en_fan", "Liberator Ril, Memory of Inspiration");
        
		setName("zh_simplified", "鼓舞的记忆 解放者莉露");
        setLRIGType(CardLRIGType.RIL);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCoins(+1);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
