package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityGuard;
import open.batoru.data.ability.stock.StockAbilityMultiEner;

public class SIGNI_X3_ServantT2 extends Card {
    
    public SIGNI_X3_ServantT2()
    {
        setImageSets("WX02-077", "WX08-083","WX13-098", "SP32-030", "PR-270", "WXK08-094");
        
        setOriginalName("サーバント　Ｔ２");
	    setAltNames("サーバントティーツー Saabanto Tii Tsuu");
        setDescription("jp",
                "~@" +
                "@C：[[マルチエナ]]"
        );
        
        setName("en", "Servant T2");
        setDescription("en",
                "~@" +
                "@C: [[Multi Ener]]"
        );
        
		setName("zh_simplified", "侍从 T2");
        setDescription("zh_simplified", 
                "[[防御]]（这张牌从手牌舍弃，能阻止一次因为分身的攻击造成的伤害）\n" +
                "@C :[[万花色]]（能量费用支付时，这张牌能作为任意颜色的1点支付）\n"
        );

        setCardFlags(CardFlag.GUARD);
        
        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClass.ORIGIN);
        setLevel(3);
        setPower(7000);
        
        setPlayFormat(PlayFormat.KEY);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerStockAbility(new StockAbilityGuard());
            registerStockAbility(new StockAbilityMultiEner());
        }
    }
}
