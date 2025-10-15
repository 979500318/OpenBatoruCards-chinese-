package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.actions.ActionDisableAllAbilities;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_G3_SpheneNaturalPyroxene extends Card {

    public SIGNI_G3_SpheneNaturalPyroxene()
    {
        setImageSets("WX25-P2-059");

        setOriginalName("羅輝石　スフェーン");
        setAltNames("ラキセキスフェーン Rakiseki Sufeen");
        setDescription("jp",
                "@C $TP：あなたの緑のシグニ１体が対戦相手の効果によって場を離れる場合、%G %Xを支払ってもよい。そうした場合、代わりにターン終了時まで、このシグニはその能力を失う。\n" +
                "@U：あなたのアタックフェイズ開始時、次の対戦相手のターン終了時まで、あなたのすべてのシグニのパワーを＋2000する。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Sphene, Natural Pyroxene");
        setDescription("en",
                "@C $TP: If 1 of your green SIGNI would leave the field by your opponent's effect, you may pay %G %X. If you do, until end of turn, this SIGNI loses its abilities instead.\n" +
                "@U: At the beginning of your attack phase, until the end of your opponent's next turn, all of your SIGNI get +2000 power." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "罗辉石 榍石");
        setDescription("zh_simplified", 
                "@C $TP :你的绿色的精灵1只因为对战对手的效果离场的场合，可以支付%G%X。这样做的场合，作为替代，直到回合结束时为止，这只精灵的这个能力失去。\n" +
                "@U :你的攻击阶段开始时，直到下一个对战对手的回合结束时为止，你的全部的精灵的力量+2000。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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

            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().withColor(CardColor.GREEN), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER, OverrideFlag.NON_MANDATORY | OverrideFlag.PRE_OVERRIDE, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler))
            );
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() != null && !isOwnCard(event.getSourceCardIndex()) && !CardLocation.isSIGNI(((EventMove)event).getMoveLocation()) &&
                   EnerCost.canPayCost(sourceAbilityRC, Cost.color(CardColor.GREEN, 1) + Cost.colorless(1));
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addEnerPayAction(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1));
            list.addAction(new ActionDisableAllAbilities(getCardIndex(), null, AbilityGain.ALLOW, ChronoDuration.turnEnd()));
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(getSIGNIOnField(getOwner()), 2000, ChronoDuration.nextTurnEnd(getOpponent()));
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
