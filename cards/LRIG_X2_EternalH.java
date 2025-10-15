package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_X2_EternalH extends Card {

    public LRIG_X2_EternalH()
    {
        setImageSets("WXDi-P11-028");

        setOriginalName("夢限 －Ｈ－");
        setAltNames("ムゲンハ Mugen Ha Mugen H");

        setName("en", "Mugen -H-");
        
        setName("en_fan", "Eternal -H-");

		setName("zh_simplified", "梦限 -H-");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MUGEN);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
