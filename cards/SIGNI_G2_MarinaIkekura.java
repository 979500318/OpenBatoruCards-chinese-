package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G2_MarinaIkekura extends Card {

    public SIGNI_G2_MarinaIkekura()
    {
        setImageSets("WX25-CP1-079");

        setOriginalName("池倉マリナ");
        setAltNames("イケクラマリナ Ikekura Marina");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に他の＜ブルアカ＞のシグニがある場合、%Gを支払ってもよい。そうした場合、ターン終了時まで、このシグニは[[ランサー（パワー8000以下のシグニ）]]と@>@C：このシグニはパワーが1000以下であるかぎり、バニッシュされない。@@を得る。" +
                "~{{U $T1：このシグニが対戦相手のライフクロス１枚をクラッシュしたとき、【エナチャージ１】をする。"
        );

        setName("en", "Ikekura Marina");

        setName("en_fan", "Marina Ikekura");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is another <<Blue Archive>> SIGNI on your field, you may pay %G. If you do, until end of turn, this SIGNI gains [[Lancer (SIGNI with power 8000 or less)]] and:" +
                "@>@C: As long as this SIGNI's power is 1000 or less, this SIGNI can't be banished.@@" +
                "~{{U $T1: When this SIGNI crushes 1 of your opponent's life cloth, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "池仓玛丽娜");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有其他的<<ブルアカ>>精灵的场合，可以支付%G。这样做的场合，直到回合结束时为止，这只精灵得到[[枪兵（力量8000以下的精灵）]]和\n" +
                "@>@C :这只精灵的力量在1000以下时，不会被破坏。@@\n" +
                "~{{U$T1 :当这只精灵把对战对手的生命护甲1张击溃时，[[能量填充1]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(5000);

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
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.CRUSH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()).getValidTargetsCount() > 0 &&
               getCardIndex().isSIGNIOnField() && payEner(Cost.color(CardColor.GREEN, 1)))
            {
                attachAbility(getCardIndex(), new StockAbilityLancer(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
                
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, this::onAttachedConstEffModRuleCheck));
                attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getPower().getValue() <= 8000 ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(RuleCheckData data)
        {
            return getPower().getValue() <= 1000 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }

        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            enerCharge(1);
        }
    }
}
