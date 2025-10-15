package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_K0_UrithEnmaOfRefinedBullets extends Card {
    
    public LRIG_K0_UrithEnmaOfRefinedBullets()
    {
        setImageSets("WDK10-005", "SPK03-14","SPK04-14","SPK15-06","SPK16-1B","SPK18-02");
        
        setOriginalName("煉弾の閻魔　ウリス");
        setAltNames("レンダンノエンマウリス Rendan no Enma Urisu");
        
        setName("en", "Urith, Enma of Refined Bullets");
        
		setName("zh_simplified", "炼弹的阎魔 乌莉丝");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.URITH);
        setColor(CardColor.BLACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
