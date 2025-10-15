package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B1_MikomikoOneMix extends Card {

    public LRIG_B1_MikomikoOneMix()
    {
        setImageSets("WXDi-P12-018");

        setOriginalName("みこみこ☆いちまぜ");
        setAltNames("ミコミコイチマゼ Mikomiko Ichi Maze");

        setName("en", "Mikomiko☆One - Mix");
        
        setName("en_fan", "Mikomiko☆One Mix");

		setName("zh_simplified", "美琴琴☆一重混音");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIKOMIKO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
