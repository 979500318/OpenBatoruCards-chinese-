package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G0_ForeclosureTaskForce extends Card {

    public LRIG_G0_ForeclosureTaskForce()
    {
        setImageSets("WXDi-CP02-037");

        setOriginalName("対策委員会");
        setAltNames("タイサクイインカイ Taisaku Iinkai");

        setName("en", "Foreclosure Task Force");
        
        
        setName("en_fan", "Foreclosure Task Force");

		setName("zh_simplified", "对策委员会");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.FORECLOSURE_TASK_FORCE);
        setColor(CardColor.GREEN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

