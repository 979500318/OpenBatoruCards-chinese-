package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W0_SaoriJoumae extends Card {
    
    public LRIG_W0_SaoriJoumae()
    {
        setImageSets("WX25-CP1-009");
        
        setOriginalName("錠前サオリ");
        setAltNames("ジョウマエサオリ Joumae Saori");
        
        setName("en", "Joumae Saori");
        
        setName("en_fan", "Saori Joumae");
        
		setName("zh_simplified", "锭前纱织");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SAORI);
        setColor(CardColor.WHITE);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
