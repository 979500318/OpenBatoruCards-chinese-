package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_MikomikoZeroZero extends Card {

    public LRIG_B0_MikomikoZeroZero()
    {
        setImageSets("WXDi-P12-017", "SPDi25-03");

        setOriginalName("みこみこ☆ぜろぜろ");
        setAltNames("ミコミコゼロゼロ Mikomiko Zero Zero");

        setName("en", "Mikomiko☆Zero - Zero");
        
        setName("en_fan", "Mikomiko☆Zero Zero");

		setName("zh_simplified", "美琴琴☆混音");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIKOMIKO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLUE);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
