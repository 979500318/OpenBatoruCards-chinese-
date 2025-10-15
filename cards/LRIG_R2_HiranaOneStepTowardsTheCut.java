package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_R2_HiranaOneStepTowardsTheCut extends Card {

    public LRIG_R2_HiranaOneStepTowardsTheCut()
    {
        setImageSets("WXDi-P13-016");

        setOriginalName("切込の一歩　ヒラナ");
        setAltNames("キリコミノイッポヒラナ Kirikomi no Ippo Hirana");

        setName("en", "Hirana, a Step Towards the Slash");
        
        
        setName("en_fan", "Hirana, One Step Towards The Cut");

		setName("zh_simplified", "切入的一步 平和");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HIRANA);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setCost(Cost.color(CardColor.RED, 1));
        setColor(CardColor.RED);
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
