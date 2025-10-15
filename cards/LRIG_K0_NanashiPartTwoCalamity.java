package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_K0_NanashiPartTwoCalamity extends Card {

    public LRIG_K0_NanashiPartTwoCalamity()
    {
        setImageSets("WXDi-P13-025");

        setOriginalName("ナナシ　其ノ弐ノ禍");
        setAltNames("ナナシソノニノカ Nanashi Sono Ni no Ka");

        setName("en", "Nanashi, Part Two Calamity");
        
        
        setName("en_fan", "Nanashi, Part Two Calamity");

		setName("zh_simplified", "无名 其之贰之祸");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NANASHI);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
