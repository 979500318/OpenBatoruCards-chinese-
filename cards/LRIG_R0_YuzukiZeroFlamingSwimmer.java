package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;

public final class LRIG_R0_YuzukiZeroFlamingSwimmer extends Card {
    
    public LRIG_R0_YuzukiZeroFlamingSwimmer()
    {
        setImageSets("WXDi-P14-010", "SPDi28-02");
        
        setOriginalName("炎泳　遊月・競露");
        setAltNames("エンエイユヅキゼロ Enei Yuzuki Zero");
        
        setName("en", "Yuzuki Water Play, Surflame");
        
        
        setName("en_fan", "Yuzuki Zero, Flaming Swimmer");
        
		setName("zh_simplified", "炎泳  游月·竞露");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUZUKI);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
