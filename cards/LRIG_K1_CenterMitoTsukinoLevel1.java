package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K1_CenterMitoTsukinoLevel1 extends Card {

    public LRIG_K1_CenterMitoTsukinoLevel1()
    {
        setImageSets("WXDi-CP01-010");

        setOriginalName("【センター】月ノ美兎　レベル１");
        setAltNames("センターツキノミトレベルイチ Sentaa Tsukino Mito Reberu Ichi Center Mito Center Tsuki");

        setName("en", "[Center] Mito, Level 1");
        
        setName("en_fan", "[Center] Mito Tsukino Level 1");

		setName("zh_simplified", "【核心】月之美兔 等级1");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MITO);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

