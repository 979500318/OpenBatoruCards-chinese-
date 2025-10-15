package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_K2_CenterMitoTsukinoLevel2 extends Card {

    public LRIG_K2_CenterMitoTsukinoLevel2()
    {
        setImageSets("WXDi-CP01-011");

        setOriginalName("【センター】月ノ美兎　レベル２");
        setAltNames("センターツキノミトレベルニ Sentaa Tsukino Mito Reberu Ni Center Mito Center Tsuki");
        
        setName("en", "[Center] Mito, Level 2");
        
        setName("en_fan", "[Center] Mito Tsukino Level 2");
        
		setName("zh_simplified", "【核心】月之美兔 等级2");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MITO);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

