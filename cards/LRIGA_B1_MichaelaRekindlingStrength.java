package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIGA_B1_MichaelaRekindlingStrength extends Card {
    
    public LRIGA_B1_MichaelaRekindlingStrength()
    {
        setImageSets("WXDi-P16-024");
        
        setOriginalName("再燃の力量　ミカエラ");
        setAltNames("サイネンノリキリョウミカエラ Sainen no Rikiryou Mikaera");
        
        setName("en", "Michaela, Rekindled Strength");
        
        
        setName("en_fan", "Michaela, Rekindling Strength");
        
		setName("zh_simplified", "再燃的力量 米卡伊来");
        setLRIGType(CardLRIGType.MICHAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
