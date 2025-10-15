package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_G2_MidorikoTwoByTwo extends Card {

    public LRIG_G2_MidorikoTwoByTwo()
    {
        setImageSets("WXDi-P13-022");

        setOriginalName("二々　緑姫");
        setAltNames("リャンリャンミドリコ Ryan Ryan Midoriko");

        setName("en", "Midoriko, Two - By - Two");
        
        
        setName("en_fan", "Midoriko, Two By Two");

		setName("zh_simplified", "二二 绿姬");
        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIDORIKO);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}

