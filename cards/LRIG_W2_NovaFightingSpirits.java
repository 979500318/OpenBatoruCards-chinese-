package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_W2_NovaFightingSpirits extends Card {
    
    public LRIG_W2_NovaFightingSpirits()
    {
        setImageSets("WXDi-P03-022");
        
        setOriginalName("闘魂　ノヴァ");
        setAltNames("ファイティングスピリッツノヴァ Faitingu Supirittsu Nova");
        
        setName("en", "Nova, Fighting Spirit");
        
        setName("en_fan", "Nova, Fighting Spirits");
        
		setName("zh_simplified", "斗魂 超");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NOVA);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
