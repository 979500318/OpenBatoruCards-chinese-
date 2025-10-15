package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R1_HanayoOne extends Card {

    public LRIG_R1_HanayoOne()
    {
        setImageSets("WD02-004", "WXDi-P10-013", "WX24-D2-02", "SP19-002","SP33-012");

        setOriginalName("花代・壱");
        setAltNames("ハナヨイチ Hanayo Ichi");

        setName("en", "Hanayo One");
        
        setName("en_fan", "Hanayo One");

		setName("zh_simplified", "花代·壹");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
