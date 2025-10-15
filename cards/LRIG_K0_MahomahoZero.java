package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K0_MahomahoZero extends Card {
    
    public LRIG_K0_MahomahoZero()
    {
        setImageSets("WXDi-P05-026", "SPDi01-27");
        
        setOriginalName("まほまほ☆ぜろ");
        setAltNames("マホマホゼロ Mahomaho Zero");
        
        setName("en", "Mahomaho☆Zero");
        
        setName("en_fan", "Mahomaho☆Zero");
        
		setName("zh_simplified", "真帆帆☆零");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MAHOMAHO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
