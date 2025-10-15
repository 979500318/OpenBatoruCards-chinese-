package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K0_NanashiPartOneCalamity extends Card {

    public LRIG_K0_NanashiPartOneCalamity()
    {
        setImageSets("WXDi-P13-024");

        setOriginalName("ナナシ　其ノ壱ノ禍");
        setAltNames("ナナシソノイチノカ Nanashi Sono Ichi no Ka");

        setName("en", "Nanashi, Part One Calamity");
        
        
        setName("en_fan", "Nanashi, Part One Calamity");

		setName("zh_simplified", "无名 其之壹之祸");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NANASHI);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
