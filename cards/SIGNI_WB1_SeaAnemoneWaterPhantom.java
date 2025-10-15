package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_WB1_SeaAnemoneWaterPhantom extends Card {

    public SIGNI_WB1_SeaAnemoneWaterPhantom()
    {
        setImageSets("WX24-P4-091");

        setOriginalName("幻水　イソギンチャク");
        setAltNames("ゲンスイイソギンチャク Gensui Isoginchaku");

        setName("en", "Sea Anemone, Water Phantom");

		setName("zh_simplified", "幻水 海葵");
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(1);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
