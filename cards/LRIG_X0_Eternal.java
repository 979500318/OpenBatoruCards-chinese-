package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_X0_Eternal extends Card {
    
    public LRIG_X0_Eternal()
    {
        setImageSets("WXK03-019", "SPK02-08A","SPK03-09","SPK04-09","SPK08-09","SPK09-09","SPK15-01","SPK16-2C","SPK22-01", "SP35-009", "PR-K033", "WXDi-P11-026", "PR-Di021", "SPDi01-72","SPDi21-07");
        
        setOriginalName("夢限");
        setAltNames("ムゲン Mugen");
        
        setName("en", "Mugen");
        
        setName("en_fan", "Eternal");
        
		setName("zh_simplified", "梦限");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MUGEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
