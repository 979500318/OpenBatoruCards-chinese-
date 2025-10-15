package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_G2_HoshinoTakanashiSwimsuitAquaticAssault extends Card {
    
    public LRIG_G2_HoshinoTakanashiSwimsuitAquaticAssault()
    {
        setImageSets("WX25-CD1-03");
        
        setOriginalName("小鳥遊ホシノ(水着)[水上襲撃]");
        setAltNames("タカナシホシノミズギスイジョウシュウゲキ Takanashi Hoshio Mizugi Suijou Shuugeki");
        
        setName("en", "Hoshino Takanashi (Swimsuit) [Aquatic Assault]");
        
		setName("zh_simplified", "小鸟游星野(泳装)[水上袭击]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HOSHINO);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
