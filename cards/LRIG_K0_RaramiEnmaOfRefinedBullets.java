package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K0_RaramiEnmaOfRefinedBullets extends Card {
    
    public LRIG_K0_RaramiEnmaOfRefinedBullets()
    {
        setImageSets("PR-K055");
        
        setOriginalName("煉弾の閻魔　ららみ");
        setAltNames("レンダンノエンマララミ Rendan no Enma Rarami");
        
        setName("en", "Rarami, Enma of Refined Bullets");
        
		setName("zh_simplified", "炼弹的阎魔 啦啦美");
        setLRIGType(CardLRIGType.URITH);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
}
