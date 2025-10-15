package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G1_HoshinoTakanashiSwimsuitFunAtTheBeach extends Card {
    
    public LRIG_G1_HoshinoTakanashiSwimsuitFunAtTheBeach()
    {
        setImageSets("WX25-CD1-02");
        
        setOriginalName("小鳥遊ホシノ(水着)[浜辺の楽しさ]");
        setAltNames("タカナシホシノミズギハマベノタノシサ Takanashi Hoshio Mizugi Hamabe no Tanoshisa");
        
        setName("en", "Hoshino Takanashi (Swimsuit) [Fun at the Beach]");
        
		setName("zh_simplified", "小鸟游星野(泳装)[海滨的快乐]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HOSHINO);
        setColor(CardColor.GREEN);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
