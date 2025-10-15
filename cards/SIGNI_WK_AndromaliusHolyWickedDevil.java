package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WK_AndromaliusHolyWickedDevil extends Card {

    public SIGNI_WK_AndromaliusHolyWickedDevil()
    {
        setImageSets("WX24-P4-093");

        setOriginalName("聖凶魔　アンドロマリウス");
        setAltNames("セイキョウマアンドロマリウス Seikyouma Andoromariusu");

        setName("en", "Andromalius, Holy Wicked Devil");

		setName("zh_simplified", "圣凶魔 安德罗玛琉斯");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
