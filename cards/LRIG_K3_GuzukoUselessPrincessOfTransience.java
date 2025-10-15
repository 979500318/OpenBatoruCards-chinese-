package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_K3_GuzukoUselessPrincessOfTransience extends Card {
    
    public LRIG_K3_GuzukoUselessPrincessOfTransience()
    {
        setImageSets("WD22-008-G", "WXK01-029");
        
        setOriginalName("泡沫の駄姫　グズ子");
        setAltNames("ウタカタノダキグズコ Utakata no Daki Guzuko");
        
        setName("en", "Guzuko, Useless Princess of Transience");
        
		setName("zh_simplified", "泡沫的驮姬 迟钝子");
        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(8);
        
        setPlayFormat(PlayFormat.KEY);
    }
}
