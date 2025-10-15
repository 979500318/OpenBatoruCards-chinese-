package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W0_TamayorihimeTHEORIGIN extends Card {
    
    public LRIG_W0_TamayorihimeTHEORIGIN()
    {
        setImageSets("PR-408");
        
        setOriginalName("タマヨリヒメ　～ＴＨＥ　ＯＲＩＧＩＮ～");
        setAltNames("タマヨリヒメジオリジン Tamayorihime ji Orijin");
        
        setName("en", "Tamayorihime ~THE ORIGIN~");
        
		setName("zh_simplified", "玉依姬  ～THE ORIGIN ～");
        setLRIGType(CardLRIGType.TAMA);
        setType(CardType.LRIG);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
