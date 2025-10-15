package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R0_LaylaNeutral extends Card {
    
    public LRIG_R0_LaylaNeutral()
    {
        setImageSets("WDK01-005", "SPK02-08C","SPK03-01","SPK04-01","SPK05-05","SPK05-06","SPK07-01","SPK08-01","SPK09-01", "SP35-001", "PR-K012", "WXDi-P09-014","SPDi01-49");
        
        setOriginalName("レイラ＝ニュートラル");
        setAltNames("レイラニュートラル Reira Nyuutoraru");
        
        setName("en", "Layla =Neutral=");
        
        setName("en_fan", "Layla-Neutral");
        
		setName("zh_simplified", "蕾拉=空挡");
        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCoins(+3);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
