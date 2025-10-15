package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RB1_CodeArtRLectricHeater extends Card {

    public SIGNI_RB1_CodeArtRLectricHeater()
    {
        setImageSets("WX24-P4-094");

        setOriginalName("コードアート　Ｒイダンボウ");
        setAltNames("コードアートアールイダンボウ Koodo Aato Aaru Idanbou");

        setName("en", "Code Art R Lectric Heater");

		setName("zh_simplified", "必杀代号 冷暖气设备");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
