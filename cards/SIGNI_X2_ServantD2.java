package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityGuard;
import open.batoru.data.ability.stock.StockAbilityMultiEner;

public class SIGNI_X2_ServantD2 extends Card {
    
    public SIGNI_X2_ServantD2()
    {
        setImageSets("WD12-016", "WD16-020","WD21-018","WD22-039-UG","WD23-042-EA", "WX02-078","WX08-084","WX13-099",
                     "SP32-033", "PR-271",
                     "WDK11-019","WDK16-19");
        
        setOriginalName("サーバント　Ｄ２");
	    setAltNames("サーバントディーツー Saabanto Dii Tsuu");
        setDescription("jp",
                "~@" +
                "@C：[[マルチエナ]]"
        );
        
        setName("en", "Servant D2");
        setDescription("en",
                "~@" +
                "@C: [[Multi Ener]]"
        );
        
		setName("zh_simplified", "侍从 D2");
        setDescription("zh_simplified", 
                "[[防御]]（这张牌从手牌舍弃，能阻止一次因为分身的攻击造成的伤害）\n" +
                "@C :[[万花色]]（能量费用支付时，这张牌能作为任意颜色的1点支付）\n"
        );

        setCardFlags(CardFlag.GUARD);
        
        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClass.ORIGIN);
        setLevel(2);
        setPower(3000);
        
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
