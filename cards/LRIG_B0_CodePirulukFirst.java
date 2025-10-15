package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_CodePirulukFirst extends Card {

    public LRIG_B0_CodePirulukFirst()
    {
        setImageSets("WXDi-P13-017");

        setOriginalName("コード・ピルルク・初");
        setAltNames("コードピルルクハジメ Koodo Piruruku Hajime");

        setName("en", "Code Piruluk Initiate");
        
        
        setName("en_fan", "Code Piruluk First");

		setName("zh_simplified", "代号·皮璐璐可·初");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
