package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G0_Mama0 extends Card {
    
    public LRIG_G0_Mama0()
    {
        setImageSets("WD20-005", "SP20-005","SP29-007","SP30-007","SP34-013","SP35-020", "PR-346", "WXDi-P09-019", "SPDi01-50","SPDi42-6P");
        
        setOriginalName("ママ♥０");
        setAltNames("ママゼロ Mama Zero");
        
        setName("en", "Mama ♥ 0");
        
        setName("en_fan", "Mama♥0");
        
		setName("zh_simplified", "妈妈♥0");
        setLRIGType(CardLRIGType.MAMA);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCoins(+4);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
