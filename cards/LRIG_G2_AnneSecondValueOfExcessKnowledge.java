package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_G2_AnneSecondValueOfExcessKnowledge extends Card {
    
    public LRIG_G2_AnneSecondValueOfExcessKnowledge()
    {
        setImageSets("SP07-003", "WX04-018", "SP33-025", "WXDi-P08-018", "WX24-P3-025");
        
        setOriginalName("過知の価値　アン＝セカンド");
        setAltNames("カチノカチアンセカンド Kachi no Kachi An Sekando");
        
        setName("en", "Ann II, Boon of Brilliance");
        
        setName("en_fan", "Anne-Second, Value of Excess Knowledge");
        
		setName("zh_simplified", "过知的价值 安=SECOND");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
