package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_R2_CarnivalS extends Card {
    
    public LRIG_R2_CarnivalS()
    {
        setImageSets("WXDi-P09-013", "WX17-012");
        
        setOriginalName("カーニバル　―S―");
        setAltNames("カーニバルセグンド Kaanibaru Segundo Carnival S");
        
        setName("en", "Carnival -S-");

        setName("en_fan", "Carnival -S-");
        
		setName("zh_simplified", "嘉年华 -S-");
        setLRIGType(CardLRIGType.CARNIVAL);
        setType(CardType.LRIG);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
}
