package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G1_MelBalalaika extends Card {

    public LRIG_G1_MelBalalaika()
    {
        setImageSets("WXDi-P09-017");

        setOriginalName("メル＝バラライカ");
        setAltNames("メルバラライカ Meru Bararaika");

        setName("en", "Mel - Balalaika");
        
        setName("en_fan", "Mel-Balalaika");

		setName("zh_simplified", "梅露=巴拉莱卡");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MEL);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
