package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_G0_MidorikoSpiritualDecree extends Card {

    public LRIG_G0_MidorikoSpiritualDecree()
    {
        setImageSets("WXDi-P13-020");

        setOriginalName("霊令　緑姫");
        setAltNames("レイレイミドリコ Rei Rei Midoriko");

        setName("en", "Midoriko, Spirit Zero");
        
        
        setName("en_fan", "Midoriko, Spiritual Decree");

		setName("zh_simplified", "灵令 绿姬");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIDORIKO);
        setColor(CardColor.GREEN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}

