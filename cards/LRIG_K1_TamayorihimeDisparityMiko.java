package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K1_TamayorihimeDisparityMiko extends Card {

    public LRIG_K1_TamayorihimeDisparityMiko()
    {
        setImageSets("WXDi-P12-024");

        setOriginalName("不等の巫女　タマヨリヒメ");
        setAltNames("フトウノミコタマヨリヒメ Futou no Miko Tamayorihime");

        setName("en", "Tamayorihime, Unequal Miko");
        
        setName("en_fan", "Tamayorihime, Disparity Miko");

		setName("zh_simplified", "不等的巫女 玉依姬");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMA);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
