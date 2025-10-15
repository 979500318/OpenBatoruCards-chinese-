package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_W2_RememberLunchMikoOfFeastingTogether extends Card {

    public LRIG_W2_RememberLunchMikoOfFeastingTogether()
    {
        setImageSets("WXDi-P10-012");

        setOriginalName("共宴の巫女　リメンバ・ランチ");
        setAltNames("キョウエンノミコリメンバランチ Kyouen no Miko Rimenba Ranchi");

        setName("en", "Remember Lunch, Miko of Feasting");
        
        setName("en_fan", "Remember Lunch, Miko of Feasting Together");

		setName("zh_simplified", "共宴的巫女 忆·午餐");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REMEMBER);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
