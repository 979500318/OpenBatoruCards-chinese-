package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIGA_B0_MichaelaRealitysDestination extends Card {
    
    public LRIGA_B0_MichaelaRealitysDestination()
    {
        setImageSets("WXDi-P16-023", "SPDi01-105");
        
        setOriginalName("現実の行方　ミカエラ");
        setAltNames("ゲンジツノユクエミカエラ Genjitsu no Yukue Mikaera");
        
        setName("en", "Michaela, Reality's Whereabouts");
        
        
        setName("en_fan", "Michaela, Reality's Destination");
        
		setName("zh_simplified", "现实的走向 米卡伊来");
        setLRIGType(CardLRIGType.MICHAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setType(CardType.LRIG);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
