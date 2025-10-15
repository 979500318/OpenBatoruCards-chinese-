package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_R0_HiranaOneStepTowardsBattle extends Card {

    public LRIG_R0_HiranaOneStepTowardsBattle()
    {
        setImageSets("WXDi-P13-014");

        setOriginalName("初陣の一歩　ヒラナ");
        setAltNames("ウイジンノイッポヒラナ Uijin no Ippo Hirana");

        setName("en", "Hirana, a Step Towards the Battle");
        
        
        setName("en_fan", "Hirana, One Step Towards Battle");

		setName("zh_simplified", "初阵的一步 平和");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HIRANA);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.RED);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
