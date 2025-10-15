package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_W1_SaoriJoumaeLeadersSenseOfResponsibility extends Card {
    
    public LRIG_W1_SaoriJoumaeLeadersSenseOfResponsibility()
    {
        setImageSets("WX25-CP1-010");
        
        setOriginalName("錠前サオリ[リーダーの責任感]");
        setAltNames("ジョウマエサオリリーダーノセキニンカン Joumae Saori Riidaa no Sekininkan");
        
        setName("en", "Joumae Saori [Leader's Sense of Responsibility]");
        
        setName("en_fan", "Saori Joumae [Leader's Sense of Responsibility]");
        
		setName("zh_simplified", "锭前纱织[身为队长的责任]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SAORI);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
