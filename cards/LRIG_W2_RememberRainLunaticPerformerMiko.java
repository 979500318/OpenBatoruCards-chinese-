package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_W2_RememberRainLunaticPerformerMiko extends Card {

    public LRIG_W2_RememberRainLunaticPerformerMiko()
    {
        setImageSets("WXDi-P13-013");

        setOriginalName("狂奏の巫女　リメンバ・レイン");
        setAltNames("キョウソウノミコリメンバクラウド Kyousou no Miko Rimenba Rein");

        setName("en", "Remember Rain, Miko of Fantasia");
        
        
        setName("en_fan", "Remember Rain, Lunatic Performer Miko");

		setName("zh_simplified", "狂奏的巫女 忆·降雨");
        setCardFlags(CardFlag.DISSONA);

        setLRIGType(CardLRIGType.REMEMBER);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
