package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.CardRuleCheckData;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockPlayerAbilitySIGNIBarrier;

public final class SIGNI_R3_CodeAccelDumpcar extends Card {

    public SIGNI_R3_CodeAccelDumpcar()
    {
        setImageSets("WX24-P1-043");
        setLinkedImageSets(Token_SIGNIBarrier.IMAGE_SET);

        setOriginalName("コードアクセル　ダンプカー");
        setAltNames("コードアクセルダンプカー Koodo Akuseru Danpukaa");
        setDescription("jp",
                "=R あなたの赤のシグニ３体の上に置く\n\n" +
                "@C：このシグニは下にレベル１のシグニがあるかぎり、@>@C：対戦相手の効果によってダウンしない。@@を得る。レベル２のシグニがあるかぎり、@>@C：対戦相手の効果によって新たに能力を得られない。@@を得る。レベル３のシグニがあるかぎり、@>@U：このシグニがアタックしたとき、対戦相手は【シグニバリア】１つを失う。@@を得る。" +
                "~#：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Code Accel Dumpcar");
        setDescription("en",
                "=R Put on 3 of your red SIGNI.\n\n" +
                "@C: As long as this SIGNI has a level 1 SIGNI under it, it gains:" +
                "@>@C: Can't be downed by your opponent's effects.@@" +
                "As long as this SIGNI has a level 2 SIGNI under it, it gains:" +
                "@>@C: Can't gain new abilities by your opponent's effects.@@" +
                "As long as this SIGNI has a level 3 SIGNI under it, it gains:" +
                "@>@U: Whenever this SIGNI attacks, your opponent loses 1 [[SIGNI Barrier]].@@" +
                "~#Target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );

		setName("zh_simplified", "加速代号 自卸卡车");
        setDescription("zh_simplified", 
                "=R在你的红色的精灵3只的上面放置\n" +
                "@C :这只精灵的下面有等级1的精灵时，得到\n" +
                "@>@C 不会因为对战对手的效果#D。@@\n" +
                "。有等级2的精灵时，得到\n" +
                "@>@C :不会因为对战对手的效果新得到能力。@@\n" +
                "。有等级3的精灵时，得到\n" +
                "@>@U :当这只精灵攻击时，对战对手把[[精灵屏障]]1个失去。@@" +
                "~#对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
        setLevel(3);
        setPower(15000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            setUseCondition(UseCondition.RISE, 3, new TargetFilter().withColor(CardColor.RED));

            registerConstantAbility(
                new AbilityGainModifier(this::onConstEffMod1Cond, this::onConstEffMod1GetSample),
                new AbilityGainModifier(this::onConstEffMod2Cond, this::onConstEffMod2GetSample),
                new AbilityGainModifier(this::onConstEffMod3Cond, this::onConstEffMod3GetSample)
            );
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffMod1Cond()
        {
            return new TargetFilter().own().SIGNI().withLevel(1).under(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffMod1GetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_DOWNED, this::onAttachedConstEff1ModRuleCheck));
        }
        private RuleCheckState onAttachedConstEff1ModRuleCheck(CardRuleCheckData data)
        {
            return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }

        private ConditionState onConstEffMod2Cond()
        {
            return new TargetFilter().own().SIGNI().withLevel(2).under(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffMod2GetSample(CardIndex cardIndex)
        {
            ConstantAbility attachedConst2 = cardIndex.getIndexedInstance().registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_ABILITY_BE_ATTACHED, this::onAttachedConstEff2ModRuleCheck));
            attachedConst2.setNestedDescriptionOffset(1);
            return attachedConst2;
        }
        private RuleCheckState onAttachedConstEff2ModRuleCheck(CardRuleCheckData data)
        {
            return data.getSourceAbility().getSourceAttachAbility() != null &&
                   !isOwnCard(data.getSourceAbility().getSourceAttachAbility().getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
        
        private ConditionState onConstEffMod3Cond()
        {
            return new TargetFilter().own().SIGNI().withLevel(3).under(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffMod3GetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setNestedDescriptionOffset(2);
            return attachedAuto;
        }
        private void onAttachedAutoEff()
        {
            CardAbilities.getPlayerAbilities(getOpponent()).stream().filter(ability -> ability.getSourceStockAbility() instanceof StockPlayerAbilitySIGNIBarrier).findFirst().
             ifPresent(CardAbilities::removePlayerAbility);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }
    }
}
