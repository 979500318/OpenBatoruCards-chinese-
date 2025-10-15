package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G0_HoshinoTakanashiSwimsuit extends Card {
    
    public LRIG_G0_HoshinoTakanashiSwimsuit()
    {
        setImageSets("WX25-CD1-01");
        
        setOriginalName("小鳥遊ホシノ(水着)");
        setAltNames("タカナシホシノミズギ Takanashi Hoshio Mizugi");
        
        setName("en", "Hoshino Takanashi (Swimsuit)");
        
		setName("zh_simplified", "小鸟游星野(泳装)");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HOSHINO);
        setColor(CardColor.GREEN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
