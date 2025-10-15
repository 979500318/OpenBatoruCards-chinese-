package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_BK1_CodeArtCAlculator extends Card {

    public SIGNI_BK1_CodeArtCAlculator()
    {
        setImageSets("WX25-P2-111");

        setOriginalName("コードアート　Dンタク");
        setAltNames("コードアートディーンタク Koodo Aato Diintaku Calculator");

        setName("en", "Code Art C Alculator");

		setName("zh_simplified", "必杀代号 计算器");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
