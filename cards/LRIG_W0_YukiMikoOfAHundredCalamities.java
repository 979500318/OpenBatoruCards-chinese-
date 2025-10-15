package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_YukiMikoOfAHundredCalamities extends Card {

    public LRIG_W0_YukiMikoOfAHundredCalamities()
    {
        setImageSets("WXDi-P12-011", "SPDi25-01");

        setOriginalName("百禍の巫女　ユキ");
        setAltNames("ヒャッカノミコユキ Hyakka no Miko Yuki");

        setName("en", "Yuki, Hundred Calamity Miko");
        
        setName("en_fan", "Yuki, Miko of a Hundred Calamities");

		setName("zh_simplified", "百祸的巫女 雪");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.WHITE);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
