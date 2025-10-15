package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G1_Mama1MODE3 extends Card {

    public LRIG_G1_Mama1MODE3()
    {
        setImageSets("WXDi-P09-020");

        setOriginalName("ママ♥１ ＭＯＤＥ３");
        setAltNames("ママワンモードスリー Mama Wan Moodo Surii");

        setName("en", "Mama ♥ 1 MODE3");
        
        setName("en_fan", "Mama♥1 MODE3");

		setName("zh_simplified", "妈妈♥1 MODE3");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MAMA);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
