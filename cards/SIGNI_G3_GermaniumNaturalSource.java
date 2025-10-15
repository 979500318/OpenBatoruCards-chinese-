package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.actions.ActionEnerPay.PaidEnerData;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.cost.AbilityCost.Payable;
import open.batoru.data.ability.cost.EnerCost;

import java.util.HashSet;
import java.util.Set;

public final class SIGNI_G3_GermaniumNaturalSource extends Card {
    
    public SIGNI_G3_GermaniumNaturalSource()
    {
        setImageSets("WXDi-D06-015", "SPDi01-24");
        
        setOriginalName("羅原　Ｇｅ");
        setAltNames("ラゲンゲルマニウム Ragen Gerumaniumu");
        setDescription("jp",
                "@E %X %X %X：このコストでトラッシュに置いたカードが持つ色が合計３種類以上ある場合、対戦相手のパワー12000以上のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2[[エナチャージ１]]"
        );
        
        setName("en", "Ge, Natural Element");
        setDescription("en",
                "@E %X %X %X: If the cards put into the trash to pay for this cost consisted of three or more colors, vanish target SIGNI on your opponent's field with power 12000 or more." +
                "~#Choose one --\n" +
                "$$1 Vanish target upped SIGNI on your opponent's field.\n" +
                "$$2 [[Ener Charge 1]]"
        );
        
        setName("en_fan", "Germanium, Natural Source");
        setDescription("en_fan",
                "@E %X %X %X: If there are 3 or more colors among the cards put into the trash for this cost, target 1 of your opponent's SIGNI with power 12000 or more, and banish it." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );
        
		setName("zh_simplified", "罗原 Ge");
        setDescription("zh_simplified", 
                "@E %X %X %X:这个费用放置到废弃区的牌的持有颜色在合计3种类以上的场合，对战对手的力量12000以上的精灵1只作为对象，将其破坏。（无色不含有颜色）" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]（你的牌组最上面的牌放置到能量区）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.colorless(3)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            DataTable<Payable> dataPaidCost = getAbility().getCostPaidData();
            Set<CardColor> cacheColors = new HashSet<>();
            for(int i=0;i<dataPaidCost.size();i++)
            {
                cacheColors.addAll(((PaidEnerData)dataPaidCost.get(i)).cardIndexSnapshot().getColor().getValue());
                
                if(cacheColors.size() >= 3)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(12000,0)).get();
                    banish(target);
                    
                    break;
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
