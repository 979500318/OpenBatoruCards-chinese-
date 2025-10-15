package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W1_RememberBreakMikoOfFeastingTogether extends Card {

    public LRIG_W1_RememberBreakMikoOfFeastingTogether()
    {
        setImageSets("WXDi-P10-011");

        setOriginalName("共宴の巫女　リメンバ・ブレック");
        setAltNames("キョウエンノミコリメンバブレック Kyouen no Miko Rimenba Burekku");

        setName("en", "Remember Break, Miko of Feasting");
        
        setName("en_fan", "Remember Break, Miko of Feasting Together");

		setName("zh_simplified", "共宴的巫女 忆·早餐");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REMEMBER);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
