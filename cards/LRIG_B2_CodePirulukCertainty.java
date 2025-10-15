package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_B2_CodePirulukCertainty extends Card {

    public LRIG_B2_CodePirulukCertainty()
    {
        setImageSets("WXDi-P13-019");

        setOriginalName("コード・ピルルク・定");
        setAltNames("コードピルルクサダメ Koodo Piruruku Sadame");

        setName("en", "Code Piruluk Decide");
        
        
        setName("en_fan", "Code Piruluk Certainty");

		setName("zh_simplified", "代号·皮璐璐可·定");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
