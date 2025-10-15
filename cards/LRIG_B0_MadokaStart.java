package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_B0_MadokaStart extends Card {
    
    public LRIG_B0_MadokaStart()
    {
        setImageSets("WXDi-D06-005", "SPDi01-17");
        
        setOriginalName("マドカ　ＳＴＡＲＴ");
        setAltNames("マドカスタート Madoka Sutaato");
        
        setName("en", "Madoka START");
        
        setName("en_fan", "Madoka START");
        
		setName("zh_simplified", "円 START");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MADOKA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLUE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
