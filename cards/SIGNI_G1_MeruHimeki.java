package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.MultiEventAutoAbility;

public final class SIGNI_G1_MeruHimeki extends Card {

    public SIGNI_G1_MeruHimeki()
    {
        setImageSets("WX25-CP1-075");

        setOriginalName("姫木メル");
        setAltNames("ヒメキメル Himeki Meru");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に他の＜ブルアカ＞のシグニがある場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@U $T1：このシグニがシグニ１体とバトルしたか、あなたのライフクロス１枚がクラッシュされたとき、ターン終了時まで、このシグニのパワーを－2000する。@@を得る。" +
                "~{{U $T1：このシグニが対戦相手のライフクロス１枚をクラッシュしたとき、【エナチャージ１】をする。"
        );

        setName("en", "Himeki Meru");

        setName("en_fan", "Meru Himeki");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is another <<Blue Archive>> SIGNI on your field, target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@U $T1: When this SIGNI battles a SIGNI or 1 of your life cloth is crushed, until end of turn, this SIGNI gets --2000 power.@@" +
                "~{{U $T1: When this SIGNI crushes 1 of your opponent's life cloth, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "姬木爱瑠");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有其他的<<ブルアカ>>精灵的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U $T1 :当这只精灵与精灵1只战斗或，你的生命护甲1张被击溃时，直到回合结束时为止，这只精灵的力量-2000。@@\n" +
                "~{{U$T1 :当这只精灵把对战对手的生命护甲1张击溃时，[[能量填充1]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(2000);

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
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
                
                if(target != null)
                {
                    MultiEventAutoAbility attachedMultiAuto = new MultiEventAutoAbility(this::onAttachedMultiAutoEff, GameEventId.ATTACK_BATTLE,GameEventId.CRUSH);
                    attachedMultiAuto.setCondition(this::onAttachedMultiAutoEffCond);
                    attachedMultiAuto.setUseLimit(UseLimit.TURN, 1);
                    
                    attachAbility(target, attachedMultiAuto, ChronoDuration.turnEnd());
                }
            }
        }
        private ConditionState onAttachedMultiAutoEffCond(CardIndex caller)
        {
            return (getEvent().getId() == GameEventId.ATTACK_BATTLE && caller == getAbility().getSourceCardIndex()) ||
                   (getEvent().getId() == GameEventId.CRUSH && !isOwnCard(caller)) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedMultiAutoEff(CardIndex caller)
        {
            getAbility().getSourceCardIndex().getIndexedInstance().gainPower(getAbility().getSourceCardIndex(), -2000, ChronoDuration.turnEnd());
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
