package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_G3_KikyouKiryuu extends Card {

    public SIGNI_G3_KikyouKiryuu()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WX25-CP1-044");

        setOriginalName("桐生キキョウ");
        setAltNames("キリュウキキョウ Kiryuu Kikyou");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが＜ブルアカ＞の場合、【エナチャージ１】をする。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの他の緑の＜ブルアカ＞のシグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000し、それは@>@C：対戦相手の効果によって新たに能力を得られない。@@を得る。" +
                "~{{A #D：【エナチャージ１】@@" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Kiryuu Kikyou");

        setName("en_fan", "Kikyou Kiryuu");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all of your SIGNI are <<Blue Archive>> SIGNI, [[Ener Charge 1]].\n" +
                "@U: At the beginning of your attack phase, target 1 of your other green <<Blue Archive>> SIGNI, and until end of turn, it gets +5000, and it gains:" +
                "@>@C: Can't gain new abilities by your opponent's effects.@@" +
                "~{{A #D: [[Ener Charge 1]]@@" +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );

		setName("zh_simplified", "桐生桔梗");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的全部的精灵是<<ブルアカ>>的场合，[[能量填充1]]。\n" +
                "@U :你的攻击阶段开始时，你的其他的绿色的<<ブルアカ>>精灵1只作为对象，直到回合结束时为止，其的力量+5000，其得到\n" +
                "@>@C :不会因为对战对手的效果新得到能力。@@\n" +
                "~{{A横置:[[能量填充1]]。@@" +
                "~#对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEffCond);

            ActionAbility act = registerActionAbility(new DownCost(), this::onActionEff);
            act.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).getValidTargetsCount() == 0)
            {
                enerCharge(1);
            }
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.GREEN).withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex())).get();
            
            if(target != null)
            {
                gainPower(target, 5000, ChronoDuration.turnEnd());
                
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_ABILITY_BE_ATTACHED, this::onAttachedConstEffModRuleCheck));
                attachAbility(target, attachedConst, ChronoDuration.turnEnd());
            }
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility().getSourceAttachAbility() != null &&
                    !isOwnCard(data.getSourceAbility().getSourceAttachAbility().getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
        
        private void onActionEff()
        {
            enerCharge(1);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}
