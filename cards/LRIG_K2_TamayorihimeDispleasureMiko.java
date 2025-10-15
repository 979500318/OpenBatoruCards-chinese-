package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_K2_TamayorihimeDispleasureMiko extends Card {

    public LRIG_K2_TamayorihimeDispleasureMiko()
    {
        setImageSets("WXDi-P12-025");

        setOriginalName("不興の巫女　タマヨリヒメ");
        setAltNames("フキョウノミコタマヨリヒメ Fukyou no Miko Tamayorihime");

        setName("en", "Tamayorihime, Displeased Miko");
        
        setName("en_fan", "Tamayorihime, Displeasure Miko");

		setName("zh_simplified", "不兴的巫女 玉依姬");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMA);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
