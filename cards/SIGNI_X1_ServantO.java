package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityGuard;
import open.batoru.data.ability.stock.StockAbilityMultiEner;

public class SIGNI_X1_ServantO extends Card {
    
    public SIGNI_X1_ServantO()
    {
        setImageSets("WD01-017", "WD13-023","WD15-022","WD17-017","WD18-017", "WX07-082",
                     "SP01-019","SP22-004", "PR-092","PR-383","PR-450","PR-453",
                     "WDK11-020","WDK16-20");
        
        setOriginalName("サーバント　Ｏ");
	    setAltNames("サーバントワン Saabanto Wan Servant One");
        setDescription("jp",
                "~@" +
                "@C：[[マルチエナ]]" +
                "~#：[[エナチャージ１]]"
        );
        
        setName("en", "Servant O");
        setDescription("en",
                "~@" +
                "@C: [[Multi Ener]]" +
                "~#[[Ener Charge 1]]"
        );
        
		setName("zh_simplified", "侍从 O");
        setDescription("zh_simplified", 
                "[[防御]]（这张牌从手牌舍弃，能阻止一次因为分身的攻击造成的伤害）\n" +
                "@C :[[万花色]]（能量费用支付时，这张牌能作为任意颜色的1点支付）" +
                "~#[[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.GUARD);
        
        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClass.ORIGIN);
        setLevel(1);
        setPower(2000);
        
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(1);
        }
    }
}
