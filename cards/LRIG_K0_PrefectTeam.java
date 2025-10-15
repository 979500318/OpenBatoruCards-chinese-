package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K0_PrefectTeam extends Card {

    public LRIG_K0_PrefectTeam()
    {
        setImageSets("WXDi-CP02-044");

        setOriginalName("風紀委員会");
        setAltNames("フウキイインカイ Fuuki Iinkai");

        setName("en", "Prefect Team");
        
        
        setName("en_fan", "Prefect Team");

		setName("zh_simplified", "风纪委员会");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PREFECT_TEAM);
        setColor(CardColor.BLACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

