package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_RememberFineLunaticPerformerMiko extends Card {

    public LRIG_W0_RememberFineLunaticPerformerMiko()
    {
        setImageSets("WXDi-P13-011");

        setOriginalName("狂奏の巫女　リメンバ・ファイン");
        setAltNames("キョウソウノミコリメンバファイン Kyousou no Miko Rimenba Fain");

        setName("en", "Remember Fine, Miko of Fantasia");
        
        
        setName("en_fan", "Remember Fine, Lunatic Performer Miko");

		setName("zh_simplified", "狂奏的巫女 忆·晴天");
        setCardFlags(CardFlag.DISSONA);

        setLRIGType(CardLRIGType.REMEMBER);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
