package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_RG1_BallElephantSmallTrap extends Card {

    public SIGNI_RG1_BallElephantSmallTrap()
    {
        setImageSets("WX24-P4-095");

        setOriginalName("小罠　ボールゾウ");
        setAltNames("ショウビンボールゾウ Shoupin Booruzou");

        setName("en", "Ball Elephant, Small Trap");

		setName("zh_simplified", "小罠 大象球技");
        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(1);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
