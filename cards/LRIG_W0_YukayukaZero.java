package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_YukayukaZero extends Card {
    
    public LRIG_W0_YukayukaZero()
    {
        setImageSets("WXDi-P05-020", "SPDi01-26");
        
        setOriginalName("ゆかゆか☆ぜろ");
        setAltNames("ユカユカゼロ Yukayuka Zero");
        
        setName("en", "Yukayuka☆Zero");
        
        setName("en_fan", "Yukayuka☆Zero");
        
		setName("zh_simplified", "由香香☆零");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUKAYUKA);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
