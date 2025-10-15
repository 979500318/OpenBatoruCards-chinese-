package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K1_NanashiPartOneAnother extends Card {

    public LRIG_K1_NanashiPartOneAnother()
    {
        setImageSets("WXDi-P09-022");

        setOriginalName("ナナシ　其ノ壱ノ別");
        setAltNames("ナナシソノイチノベツ Nanashi Sono Ichi no Betsu");

        setName("en", "Nanashi, Part One Another");
        
        setName("en_fan", "Nanashi, Part One Another");

		setName("zh_simplified", "无名 其之壹之别");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NANASHI);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
