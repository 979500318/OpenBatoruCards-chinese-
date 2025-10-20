package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityGuard;
import open.batoru.data.ability.stock.StockAbilityMultiEner;

public final class SIGNI_X1_ServantSharp extends Card {
    
    public SIGNI_X1_ServantSharp()
    {
        setImageSets("WXDi-D01-020", "WXDi-D02-28","WXDi-D07-021","WXDi-D08-021", "WXDi-P00-090","WXDi-P00-091",
        "WXDi-P03-096","WXDi-P04-093","WXDi-P05-093","WXDi-P06-092","WXDi-P07-100",
        "WXDi-P08-082","WXDi-P09-084","WXDi-P10-078","WXDi-P11-084","WXDi-P12-090",
        "WXDi-P13-090","WXDi-P14-090","WXDi-CP02-104","WXDi-P15-099","WXDi-P16-095",
        "WX24-P1-086","WX24-P2-095","WX24-P3-094","WX24-P4-100","WX25-CP1-095","WX25-P1-116","WX25-P2-116",
        "SPDi02-03","SPDi02-06","SPDi02-07","SPDi02-08","SPDi02-09","SPDi02-10",
        "SPDi02-11","SPDi02-12","SPDi02-13","SPDi12-01","SPDi12-02","SPDi12-03","SPDi12-04","SPDi12-05","SPDi12-06",
        "SPDi20-01","SPDi20-02","SPDi20-03","SPDi20-04","SPDi20-05","SPDi20-06",
        "SPDi26-02","SPDi26-03","SPDi26-04","SPDi26-05","SPDi26-06","SPDi26-07","SPDi26-08","SPDi26-09","SPDi26-10","SPDi26-11",
        "SPDi29-02","SPDi30-01","SPDi33-01","SPDi33-02","SPDi33-03","SPDi33-04","SPDi33-05","SPDi39-01","SPDi39-02","SPDi39-03",
        "SPDi44-17",
        "PR-Di001","PR-Di009","PR-Di010","PR-Di016","PR-Di018","PR-Di019","PR-Di024","PR-Di032","PR-Di049");
        
        setOriginalName("サーバント　#");
        setAltNames("サーバントシャープ Saabanto Shaapu Servant Sharp Servant ♯");
        setDescription("jp",
                "~@" +
                "@C：[[マルチエナ]]" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Servant #");
        setDescription("en",
                "~@" +
                "@C: [[Multi Ener]]" +
                "~#Add target SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Servant #");
        setDescription("en_fan",
                "~@" +
                "@C: [[Multi Ener]]" +
                "~#Target 1 SIGNI from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "侍从 ＃");
        setDescription("zh_simplified", 
                "[[防御]]（这张牌从手牌舍弃，能阻止一次因为分身的攻击造成的伤害）\n" +
                "@C :[[万花色]]（能量费用支付时，这张牌能作为任意颜色的1点支付）" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.GUARD);
        
        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ORIGIN);
        setLevel(1);
        setPower(1000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}
