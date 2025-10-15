package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.AbilityCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityCostModifier;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class SIGNI_G3_ShirokoSunaookamiSwimsuit extends Card {

    public SIGNI_G3_ShirokoSunaookamiSwimsuit()
    {
        setImageSets("WX25-CD1-17");

        setOriginalName("砂狼シロコ(水着)");
        setAltNames("スナオオカミシロコミズギ Sunaookami Shiroko Mizugi");
        setDescription("jp",
                "@E：あなたの場に他の＜ブルアカ＞のシグニがある場合、このターン、次にあなたが使用するルリグの@A能力の使用コストは%X減る。" +
                "~{{U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、あなたのエナゾーンから＜ブルアカ＞のカード１枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、それの基本パワーを3000にする。@@" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Shiroko Sunaookami (Swimsuit)");
        setDescription("en",
                "@E: If there is another <<Blue Archive>> SIGNI on your field, this turn, the use cost of the next LRIG @A you use is reduced by %X." +
                "~{{U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may put 1 <<Blue Archive>> card from your ener zone into the trash. If you do, until end of turn, its base power becomes 3000.@@" +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "砂狼白子(泳装)");
        setDescription("zh_simplified", 
                "@E 你的场上有其他的<<ブルアカ>>精灵的场合，这个回合，下一次你使用的分身的@A能力的使用费用减%X。\n" +
                "~{{U:你的攻击阶段开始时，对战对手的精灵1只作为对象，可以从你的能量区把<<ブルアカ>>牌1张放置到废弃区。这样做的场合，直到回合结束时为止，其的基本力量变为3000。@@" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private int cacheUsedLRIGActCount;
        private void onEnterEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                cacheUsedLRIGActCount = GameLog.getTurnRecordsCount(event ->
                    event.getId() == GameEventId.ABILITY &&
                    event.getSourceAbility() instanceof ActionAbility && isOwnCard(event.getCaller()) &&
                    CardType.isLRIG(event.getCaller().getCardReference().getType())
                );
                
                ConstantAbility attachedConst = new ConstantAbilityShared(new TargetFilter().own().anyLRIG(),
                    new AbilityCostModifier(ability -> ability instanceof ActionAbility, new CostModifier(this::onAttachedConstEffModGetSample, ModifierMode.REDUCE))
                );
                attachedConst.setCondition(this::onAttachedConstEffCond);
                
                attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedConstEffCond(CardIndex cardIndex)
        {
            return GameLog.getTurnRecordsCount(event ->
                    event.getId() == GameEventId.ABILITY &&
                    event.getSourceAbility() instanceof ActionAbility && isOwnCard(event.getCaller()) &&
                    CardType.isLRIG(event.getCaller().getCardReference().getType())) == cacheUsedLRIGActCount ? ConditionState.OK : ConditionState.BAD;
        }
        private AbilityCost onAttachedConstEffModGetSample()
        {
            return new EnerCost(Cost.colorless(1));
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()).get();
                
                if(trash(cardIndex))
                {
                    setBasePower(target, 3000, ChronoDuration.turnEnd());
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
