package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_G2_MelSnowCountry extends Card {

    public LRIG_G2_MelSnowCountry()
    {
        setImageSets("WXDi-P12-022");

        setOriginalName("メル＝雪国");
        setAltNames("メルユキグニ Meru Yukiguni");

        setName("en", "Mel - Snow Country");
        
        setName("en_fan", "Mel-Snow Country");

		setName("zh_simplified", "梅露=雪国");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MEL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
