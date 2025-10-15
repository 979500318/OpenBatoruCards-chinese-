package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K1_AlfouReflectiveWaterDiva extends Card {
    
    public LRIG_K1_AlfouReflectiveWaterDiva()
    {
        setImageSets("WXDi-P11-021");
        
        setOriginalName("水鏡の歌姫　アルフォウ");
        setAltNames("スイキョウノウタヒメアルフォウ Suikyuu no Utahime Arufou");
        
        setName("en", "Alfou, Reflective Diva");
        
        setName("en_fan", "Alfou, Reflective Water Diva");
        
		setName("zh_simplified", "水镜的歌姬 阿尔芙");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ALFOU);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
