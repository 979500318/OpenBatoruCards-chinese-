package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RK1_MarracoPhantomDragon extends Card {

    public SIGNI_RK1_MarracoPhantomDragon()
    {
        setImageSets("WX24-P4-096");

        setOriginalName("幻竜　マラク");
        setAltNames("ゲンリュウマラク Genryuu Maraku");

        setName("en", "Marraco, Phantom Dragon");

		setName("zh_simplified", "幻龙 马拉克");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(1);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
