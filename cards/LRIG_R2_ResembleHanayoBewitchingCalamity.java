package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_R2_ResembleHanayoBewitchingCalamity extends Card {

    public LRIG_R2_ResembleHanayoBewitchingCalamity()
    {
        setImageSets("WXDi-P12-016");

        setOriginalName("妖禍　花代・似");
        setAltNames("ヨウカハナヨニ Youka Hanayo Ni");

        setName("en", "Hanayo Mimic, Ominous Disaster");
        
        setName("en_fan", "Resemble Hanayo, Bewitching Calamity");

		setName("zh_simplified", "妖祸 花代·似");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
