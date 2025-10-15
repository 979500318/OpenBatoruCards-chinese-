package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_K1_MahomahoOne extends Card {
    
    public LRIG_K1_MahomahoOne()
    {
        setImageSets("WXDi-P07-011");
        
        setOriginalName("まほまほ☆いち");
        setAltNames("マホマホイチ Mahomaho Ichi Mahomaho One");
        
        setName("en", "Mahomaho☆One");
        
        setName("en_fan", "Mahomaho☆One");
        
		setName("zh_simplified", "真帆帆☆壹");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MAHOMAHO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLACK);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
