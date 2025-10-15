package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R1_LaylaTurbo extends Card {

    public LRIG_R1_LaylaTurbo()
    {
        setImageSets("WXDi-P09-015");

        setOriginalName("レイラ＝ターボ");
        setAltNames("レイラターボ Reira Taabo");

        setName("en", "Layla =Turbo=");
        
        setName("en_fan", "Layla-Turbo");

		setName("zh_simplified", "蕾拉=涡轮");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LAYLA);
        setColor(CardColor.RED);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
