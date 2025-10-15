package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W1_YukiMikoOfPurityAndImpurity extends Card {

    public LRIG_W1_YukiMikoOfPurityAndImpurity()
    {
        setImageSets("WXDi-P12-012");

        setOriginalName("清濁の巫女　ユキ");
        setAltNames("セイダクノミコユキ Seidaku no Miko Yuki");

        setName("en", "Yuki, Pure and Impure Miko");
        
        setName("en_fan", "Yuki, Miko of Purity and Impurity");

		setName("zh_simplified", "清浊的巫女 雪");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
