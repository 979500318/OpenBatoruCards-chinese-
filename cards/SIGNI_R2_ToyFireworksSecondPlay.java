package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_ToyFireworksSecondPlay extends Card {

    public SIGNI_R2_ToyFireworksSecondPlay()
    {
        setImageSets("WX25-P2-077");

        setOriginalName("弍ノ遊　センコウハナビ");
        setAltNames("ニノユウセンコウハナビ Ni no Yuu Senkouhanabi");
        setDescription("jp",
                "@U $TO $T1：あなたの効果によって他の＜遊具＞のシグニ１体が場に出たとき、対戦相手のパワー5000以下のシグニ１体を対象とし、あなたのエナゾーンから＜遊具＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Toy Fireworks, Second Play");
        setDescription("en",
                "@U $TO $T1: When another 1 of your <<Playground Equipment>> SIGNI enters the field by your effect, target 1 of your opponent's SIGNI with power 5000 or less, and you may put 1 <<Playground Equipment>> SIGNI from your ener zone into the trash. If you do, banish it." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may pay %X. If you do, banish it."
        );

		setName("zh_simplified", "贰之游 线香烟花");
        setDescription("zh_simplified", 
                "@U $TO $T1 :当因为你的效果把其他的<<遊具>>精灵1只出场时，对战对手的力量5000以下的精灵1只作为对象，可以从你的能量区把<<遊具>>精灵1张放置到废弃区。这样做的场合，将其破坏。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && caller != getCardIndex() && isOwnCard(caller) &&
                   CardType.isSIGNI(caller.getCardReference().getType()) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.PLAYGROUND_EQUIPMENT) &&
                   getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSource()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).fromEner()).get();
                
                if(trash(cardIndex))
                {
                    banish(target);
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
