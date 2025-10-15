package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_R1_HiranaOneStepAheadOfTheCurve extends Card {

    public LRIG_R1_HiranaOneStepAheadOfTheCurve()
    {
        setImageSets("WXDi-P13-015", "WXDi-D09-H02");

        setOriginalName("先駆の一歩　ヒラナ");
        setAltNames("サキガケノイッポヒラナ Sakigake no Ippo Hirana");

        setName("en", "Hirana, a Step Towards the Initiative");
        
        
        setName("en_fan", "Hirana, One Step Ahead of the Curve");

		setName("zh_simplified", "先驱的一步 平和");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HIRANA);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
