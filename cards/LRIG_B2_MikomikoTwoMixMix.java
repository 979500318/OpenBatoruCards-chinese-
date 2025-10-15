package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_B2_MikomikoTwoMixMix extends Card {

    public LRIG_B2_MikomikoTwoMixMix()
    {
        setImageSets("WXDi-P12-019");

        setOriginalName("みこみこ☆にまぜまぜ");
        setAltNames("ミコミコニマゼマゼ Mikomiko Ni Maze Maze");

        setName("en", "Mikomiko☆Two Mix - Mix");
        
        setName("en_fan", "Mikomiko☆Two Mix Mix");

		setName("zh_simplified", "美琴琴☆双重混音");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIKOMIKO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
