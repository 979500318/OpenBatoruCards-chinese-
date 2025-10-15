package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_R2_LaylaDrag extends Card {

    public LRIG_R2_LaylaDrag()
    {
        setImageSets("WXDi-P09-016");

        setOriginalName("レイラ＝ドラッグ");
        setAltNames("レイラドラッグ Reira Doraggu");

        setName("en", "Layla =Drag=");
        
        setName("en_fan", "Layla-Drag");

		setName("zh_simplified", "蕾拉=拖曳");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LAYLA);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
