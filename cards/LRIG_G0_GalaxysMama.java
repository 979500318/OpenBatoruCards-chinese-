package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_G0_GalaxysMama extends Card {
    
    public LRIG_G0_GalaxysMama()
    {
        setImageSets("WXDi-P09-019-M02", "SPDi17-02");
        
        setOriginalName("ギャラクシー母");
        setAltNames("ギャラクシーハハ Gyarakushii Haha");
        
        setName("en", "Galaxy's Mama");
        
        
        setName("en_fan", "Galaxy's Mama");
        
		setName("zh_simplified", "银河母");
        setLRIGType(CardLRIGType.MAMA);
        setType(CardType.LRIG);
        setColor(CardColor.GREEN);
        setCoins(+4);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
