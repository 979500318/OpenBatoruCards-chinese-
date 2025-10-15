package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_G2_ShirokoSunaookamiHandGrenade extends Card {
    
    public LRIG_G2_ShirokoSunaookamiHandGrenade()
    {
        setImageSets("WXDi-CP02-019");
        
        setOriginalName("砂狼シロコ[手榴弾投擲]");
        setAltNames("スナオオカミシロコシュリュウダントウテキ Sunaookami Shiroko Shuryuudan Touteki");
        
        setName("en", "Sunaookami Shiroko [Grenade Lob]");
        
        
        setName("en_fan", "Shiroko Sunaookami [Hand Grenade]");
        
		setName("zh_simplified", "砂狼白子[手榴弹投掷]");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SHIROKO);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}

