package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_R2_HanayoTwo extends Card {

    public LRIG_R2_HanayoTwo()
    {
        setImageSets("WD02-003", "WXDi-P10-014", "WX24-D2-03", "SP33-011");

        setOriginalName("花代・爾");
        setAltNames("ハナヨニ Hanayo Ni");

        setName("en", "Hanayo Two");
        
        setName("en_fan", "Hanayo Two");

		setName("zh_simplified", "花代·贰");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
