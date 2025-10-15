package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_BK1_NightPoolFirstPlay extends Card {

    public SIGNI_BK1_NightPoolFirstPlay()
    {
        setImageSets("WX24-P4-098");

        setOriginalName("壱ノ遊　ナイトプール");
        setAltNames("イチノユウナイトプール Ichi no Yuu Naito Puuru");

        setName("en", "Night Pool, First Play");

		setName("zh_simplified", "壹之游 夜间泳池");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(1);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
