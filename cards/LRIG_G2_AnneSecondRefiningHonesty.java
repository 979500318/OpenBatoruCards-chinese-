package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_G2_AnneSecondRefiningHonesty extends Card {
    
    public LRIG_G2_AnneSecondRefiningHonesty()
    {
        setImageSets("WXDi-P14-018");
        
        setOriginalName("清廉の精錬　アン＝セカンド");
        setAltNames("セイレンノセイレンアンセカンド Seiren no Seiren An Sekando");
        
        setName("en", "Ann II, Principled Practice");
        
        
        setName("en_fan", "Anne-Second, Refining Honesty");
        
		setName("zh_simplified", "清廉的精炼 安=SECOND");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
