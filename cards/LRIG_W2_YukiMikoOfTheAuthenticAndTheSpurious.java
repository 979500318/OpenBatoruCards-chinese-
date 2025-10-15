package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_W2_YukiMikoOfTheAuthenticAndTheSpurious extends Card {

    public LRIG_W2_YukiMikoOfTheAuthenticAndTheSpurious()
    {
        setImageSets("WXDi-P12-013");

        setOriginalName("真贋の巫女　ユキ");
        setAltNames("シンガンノミコユキ Shingan no Miko Yuki");

        setName("en", "Yuki, Authentic Miko");
        
        setName("en_fan", "Yuki, Miko of the Authentic and the Spurious");

		setName("zh_simplified", "真赝的巫女 雪");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
