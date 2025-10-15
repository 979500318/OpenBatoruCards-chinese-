package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_R0_HanayoDark extends Card {

    public LRIG_R0_HanayoDark()
    {
        setImageSets("WXDi-P12-014", "SPDi25-02");

        setOriginalName("花代・黎");
        setAltNames("ハナヨレイ Hanayo Rei");

        setName("en", "Hanayo, Daybreak");
        
        setName("en_fan", "Hanayo Dark");

		setName("zh_simplified", "花代·黎");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
