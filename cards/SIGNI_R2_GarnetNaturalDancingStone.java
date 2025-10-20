package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R2_GarnetNaturalDancingStone extends Card {
    
    public SIGNI_R2_GarnetNaturalDancingStone()
    {
        setImageSets("WXDi-P08-059");
        
        setOriginalName("羅踊石　ガーネット");
        setAltNames("ラヨウセキガーネット Rayouseki Gaanetto");
        setDescription("jp",
                "@C：このシグニがダウン状態であるかぎり、このシグニのパワーは＋4000される。\n" +
                "@A #D：対戦相手のエナゾーンからカード１枚を対象とし、それをトラッシュに置く。そうした場合、対戦相手は【エナチャージ１】をしてもよい。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Garnet, Natural Dancing Stone");
        setDescription("en",
                "@C: As long as this SIGNI is downed, this SIGNI gets +4000 power.\n" +
                "@A #D: Put target card from your opponent's Ener Zone into their trash. If you do, your opponent may [[Ener Charge 1]]." +
                "~#You may discard a card. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Garnet, Natural Dancing Stone");
        setDescription("en_fan",
                "@C: As long as this SIGNI is downed, it gets +4000 power.\n" +
                "@A #D: Target 1 card from your opponent's ener zone, and put it into the trash. If you do, your opponent may [[Ener Charge 1]]." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may discard 1 card from your hand. If you do, banish it."
        );
        
		setName("zh_simplified", "罗踊石 石榴石");
        setDescription("zh_simplified", 
                "@C :这只精灵在横置状态时，这只精灵的力量+4000。\n" +
                "@A 横置:从对战对手的能量区把1张牌作为对象，将其放置到废弃区。这样做的场合，对战对手可以[[能量填充1]]。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            registerActionAbility(new DownCost(), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return isState(CardStateFlag.DOWNED) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner()).get();
            
            if(trash(target) && playerChoiceActivate(getOpponent()))
            {
                enerCharge(getOpponent(), 1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                banish(target);
            }
        }
    }
}
