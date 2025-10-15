package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K0_SutekoUselessPrincess extends Card {
    
    public LRIG_K0_SutekoUselessPrincess()
    {
        setImageSets("PR-K025");
        
        setOriginalName("駄姫　ステコ");
        setAltNames("ダキステコ Daki Suteko");
        
        setName("en", "Suteko, Useless Princess");
        
		setName("zh_simplified", "驮姬 寿子");
        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setCoins(+3);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
