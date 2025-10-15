package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G1_MidorikoOneByOne extends Card {

    public LRIG_G1_MidorikoOneByOne()
    {
        setImageSets("WXDi-P13-021");

        setOriginalName("一々　緑姫");
        setAltNames("イチイチミドリコ Ichi Ichi Midoriko");

        setName("en", "Midoriko, One - By - One");
        
        
        setName("en_fan", "Midoriko, One by One");

		setName("zh_simplified", "一一 绿姬");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIDORIKO);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}

