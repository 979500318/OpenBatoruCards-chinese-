package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_B0_AyameMizukuki extends Card {
    
    public LRIG_B0_AyameMizukuki()
    {
        setImageSets("SPDi16-02");
        
        setOriginalName("水茎あやめ");
        setAltNames("ミズクキアヤメ Mizukuki Ayame");
        
        setName("en", "Ayame Mizukuki");
        
		setName("zh_simplified", "水茎绫女");
        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
