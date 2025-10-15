package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_R0_RilMemoryOfInnocence extends Card {
    
    public LRIG_R0_RilMemoryOfInnocence()
    {
        setImageSets("WD17-005", "SP20-002","SP21-002","SP23-005","SP29-002","SP30-002","SP34-010","SP35-007",
                     "PR-343",Mask.IGNORE+"PR-357",
                     "WDK06-R05", "SPK03-07","SPK04-07","SPK05-03","SPK05-04","SPK07-07","SPK08-07","SPK09-07","SPK14-02","SPK16-4B",
                     "WXDi-P07-013", "SPDi01-37","SPDi14-05","SPDi15-02","SPDi21-02","SPDi23-13");
        
        setOriginalName("純真の記憶　リル");
        setAltNames("ジュンシンノキオクリル Junshin no Kioku Riru");
        
        setName("en", "Ril, Memory of Innocence");
        
        setName("en_fan", "Ril, Memory of Innocence");
        
		setName("zh_simplified", "纯真的记忆 莉露");
        setLRIGType(CardLRIGType.RIL);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCoins(+1);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
