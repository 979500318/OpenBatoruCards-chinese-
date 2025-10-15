package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_W2_SaoriJoumaeImmediateFiring extends Card {
    
    public LRIG_W2_SaoriJoumaeImmediateFiring()
    {
        setImageSets("WX25-CP1-011");
        
        setOriginalName("錠前サオリ[即時射撃]");
        setAltNames("ジョウマエサオリソクジシャゲキ Joumae Saori Sokuji Shageki");
        
        setName("en", "Joumae Saori [Immediate Firing]");
        
        setName("en_fan", "Saori Joumae [Immediate Firing]");
        
		setName("zh_simplified", "锭前纱织[指向射击]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SAORI);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
