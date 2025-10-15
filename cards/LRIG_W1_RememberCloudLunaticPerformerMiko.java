package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W1_RememberCloudLunaticPerformerMiko extends Card {

    public LRIG_W1_RememberCloudLunaticPerformerMiko()
    {
        setImageSets("WXDi-P13-012");

        setOriginalName("狂奏の巫女　リメンバ・クラウド");
        setAltNames("キョウソウノミコリメンバクラウド Kyousou no Miko Rimenba Kuraudo");

        setName("en", "Remember Cloud, Miko of Fantasia");
        
        
        setName("en_fan", "Remember Cloud, Lunatic Performer Miko");

		setName("zh_simplified", "狂奏的巫女 忆·多云");
        setCardFlags(CardFlag.DISSONA);

        setLRIGType(CardLRIGType.REMEMBER);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
