package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K0_TamayorihimeDiscordMiko extends Card {

    public LRIG_K0_TamayorihimeDiscordMiko()
    {
        setImageSets("WXDi-P12-023", "SPDi25-05");

        setOriginalName("不和の巫女　タマヨリヒメ");
        setAltNames("フワノミコタマヨリヒメ Fuwa no Miko Tamayorihime");

        setName("en", "Tamayorihime, Discordant Miko");
        
        setName("en_fan", "Tamayorihime, Discord Miko");

		setName("zh_simplified", "不和的巫女 玉依姬");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMA);
        setColor(CardColor.BLACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
