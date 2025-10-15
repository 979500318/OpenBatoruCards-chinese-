package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_BG1_WaterArtAzureVerdantBeauty extends Card {

    public SIGNI_BG1_WaterArtAzureVerdantBeauty()
    {
        setImageSets("WX24-P4-097");

        setOriginalName("蒼翠美　ウォーターアート");
        setAltNames("ソウスイビウォーターアート Sousuibi Uootaa Aato");

        setName("en", "Water Art, Azure Verdant Beauty");

		setName("zh_simplified", "苍翠美 水画");
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(1);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
