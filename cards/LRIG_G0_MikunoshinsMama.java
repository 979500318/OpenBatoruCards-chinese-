package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G0_MikunoshinsMama extends Card {
    public LRIG_G0_MikunoshinsMama()
    {
        setImageSets("WXDi-P09-019-M03", "SPDi17-03");
        
        setOriginalName("みくのしん母");
        setAltNames("ミクノシンハハ Miku no Shin Haha");
        
        setName("en", "Mikunoshin's Mama");
        
        
        setName("en_fan", "Mikunoshin's Mama");
        
		setName("zh_simplified", "未来神母");
        setLRIGType(CardLRIGType.MAMA);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCoins(+4);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
