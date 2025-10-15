package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K0_CenterMitoTsukinoLevel0 extends Card {

    public LRIG_K0_CenterMitoTsukinoLevel0()
    {
        setImageSets("WXDi-CP01-009", "SPDi01-73");

        setOriginalName("【センター】月ノ美兎　レベル０");
        setAltNames("センターツキノミトレベルゼロ Sentaa Tsukino Mito Reberu Zero Center Mito Center Tsuki");

        setName("en", "Mito, Level 0");
        
        setName("en_fan", "[Center] Mito Tsukino Level 0");

		setName("zh_simplified", "【核心】月之美兔 等级0");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MITO);
        setColor(CardColor.BLACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

