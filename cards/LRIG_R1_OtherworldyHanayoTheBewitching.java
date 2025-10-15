package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_R1_OtherworldyHanayoTheBewitching extends Card {

    public LRIG_R1_OtherworldyHanayoTheBewitching()
    {
        setImageSets("WXDi-P12-015");

        setOriginalName("妖　花代・異地");
        setAltNames("ヨウハナヨイチ You Hanayo Ichi");

        setName("en", "Hanayo, Strange Land Fairy");
        
        setName("en_fan", "Otherworldly Hanayo, the Bewitching");

		setName("zh_simplified", "妖 花代·异地");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
