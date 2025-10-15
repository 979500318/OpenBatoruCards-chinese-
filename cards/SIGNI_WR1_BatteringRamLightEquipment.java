package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WR1_BatteringRamLightEquipment extends Card {

    public SIGNI_WR1_BatteringRamLightEquipment()
    {
        setImageSets("WX24-P4-090");

        setOriginalName("小装　ハジョウツイ");
        setAltNames("ショウソウハジョウツイ Shousou Hajoutsui");

        setName("en", "Battering Ram, Light Equipment");

		setName("zh_simplified", "小装 破城槌");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(1);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
