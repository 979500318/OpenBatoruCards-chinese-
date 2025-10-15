package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityGuard;
import open.batoru.data.ability.stock.StockAbilityMultiEner;

public class SIGNI_X1_ServantO2 extends Card {
    
    public SIGNI_X1_ServantO2()
    {
        setImageSets("WD04-017", "WD12-017","WD16-021","WD21-019","WD22-040-UG","WD23-043-EA", "WX08-085","WX13-100",
                     "SP06-019","SP32-036", "PR-272","PR-449",
                     "WDK11-021","WDK16-21");
        
        setOriginalName("サーバント　Ｏ２");
	    setAltNames("サーバントオーツー Saabanto Oo Tsuu");
        setDescription("jp",
                "~@" +
                "@C：[[マルチエナ]]"
        );
        
        setName("en", "Servant O2");
        setDescription("en",
                "~@" +
                "@C: [[Multi Ener]]"
        );
        
		setName("zh_simplified", "侍从 O2");
        setDescription("zh_simplified", 
                "[[防御]]（这张牌从手牌舍弃，能阻止一次因为分身的攻击造成的伤害）\n" +
                "@C :[[万花色]]（能量费用支付时，这张牌能作为任意颜色的1点支付）\n"
        );

        setCardFlags(CardFlag.GUARD);
        
        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClass.ORIGIN);
        setLevel(1);
        setPower(1000);
        
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
