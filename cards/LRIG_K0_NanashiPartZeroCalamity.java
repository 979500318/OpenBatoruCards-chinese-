package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K0_NanashiPartZeroCalamity extends Card {

    public LRIG_K0_NanashiPartZeroCalamity()
    {
        setImageSets("WXDi-P13-023");

        setOriginalName("ナナシ　其ノ零ノ禍");
        setAltNames("ナナシソノゼロノカ Nanashi Sono Zero no Ka");

        setName("en", "Nanashi, Part Zero Calamity");
        
        setName("en_fan", "Nanashi, Part Zero Calamity");

		setName("zh_simplified", "无名 其之零之祸");
        setDescription("zh_simplified", 
                "（游戏开始时，当这张分身表向时，如果这只分身是核心分身，那么得到#C #C）\n" +
                "（当右下持有[币]的分身成长时，得到与其相同枚数的币）\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NANASHI);
        setColor(CardColor.BLACK);
        setCoins(+2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
