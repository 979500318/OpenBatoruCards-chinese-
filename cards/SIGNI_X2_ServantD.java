package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityGuard;
import open.batoru.data.ability.stock.StockAbilityMultiEner;

public class SIGNI_X2_ServantD extends Card {
    
    public SIGNI_X2_ServantD()
    {
        setImageSets("WD01-016", "WD08-017","WD13-022","WD15-021","WD17-016","WD18-016", "WX07-081",
                     "SP01-018","SP22-003","SP32-031", "PR-118","PR-382","PR-454",
                     "WXK08-091"
        );
        
        setOriginalName("サーバント　Ｄ");
	    setAltNames("サーバントデュオ Saabanto Duo");
        setDescription("jp",
                "~@" +
                "@C：[[マルチエナ]]" +
                "~#：[[エナチャージ１]]"
        );
        
        setName("en", "Servant D");
        setDescription("en",
                "~@" +
                "@C: [[Multi Ener]]" +
                "~#[[Ener Charge 1]]"
        );
        
		setName("zh_simplified", "侍从 D");
        setDescription("zh_simplified", 
                "[[防御]]（这张牌从手牌舍弃，能阻止一次因为分身的攻击造成的伤害）\n" +
                "@C :[[万花色]]（能量费用支付时，这张牌能作为任意颜色的1点支付）" +
                "~#[[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.GUARD);
        
        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClass.ORIGIN);
        setLevel(2);
        setPower(5000);
        
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
