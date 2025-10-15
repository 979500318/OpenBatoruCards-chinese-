package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G0_MelCherryBlossom extends Card {

    public LRIG_G0_MelCherryBlossom()
    {
        setImageSets("WXDi-P12-020", "SPDi25-04");

        setOriginalName("メル＝桜花");
        setAltNames("メルオウカ Meru Sakura");

        setName("en", "Mel - Cherry Blossom");
        
        setName("en_fan", "Mel-Cherry Blossom");

		setName("zh_simplified", "梅露=樱花");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MEL);
        setColor(CardColor.GREEN);
        setCoins(+3);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
