package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W0_RememberMikoOfFeastingTogether extends Card {

    public LRIG_W0_RememberMikoOfFeastingTogether()
    {
        setImageSets("WXDi-P10-010", "SPDi01-56");

        setOriginalName("共宴の巫女　リメンバ");
        setAltNames("キョウエンノミコリメンバ Kyouen no Miko Rimenba");

        setName("en", "Remember, Miko of Feasting");
        
        setName("en_fan", "Remember, Miko of Feasting Together");

		setName("zh_simplified", "共宴的巫女 忆");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REMEMBER);
        setColor(CardColor.WHITE);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
