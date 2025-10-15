package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B1_CodePirulukProgress extends Card {

    public LRIG_B1_CodePirulukProgress()
    {
        setImageSets("WXDi-P13-018");

        setOriginalName("コード・ピルルク・進");
        setAltNames("コードピルルクススメ Koodo Piruruku Susume");

        setName("en", "Code Piruluk Advance");
        
        
        setName("en_fan", "Code Piruluk Progress");

		setName("zh_simplified", "代号·皮璐璐可·进");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
