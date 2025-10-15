package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WG_ChristmasRoseNaturalPlant extends Card {

    public SIGNI_WG_ChristmasRoseNaturalPlant()
    {
        setImageSets("WX24-P4-092");

        setOriginalName("羅植　クリスマスローズ");
        setAltNames("ラショククリスマスローズ Rasoku Kurisumasu Roozu");

        setName("en", "Christmas Rose, Natural Plant");

		setName("zh_simplified", "罗植 圣诞蔷薇");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(1);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
