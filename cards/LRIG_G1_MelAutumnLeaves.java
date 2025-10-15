package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G1_MelAutumnLeaves extends Card {

    public LRIG_G1_MelAutumnLeaves()
    {
        setImageSets("WXDi-P12-021");

        setOriginalName("メル＝照葉");
        setAltNames("メルテリハ Meru Teriha");

        setName("en", "Mel - Evergreen");
        
        setName("en_fan", "Mel-Autumn Leaves");

		setName("zh_simplified", "梅露=照叶");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MEL);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
