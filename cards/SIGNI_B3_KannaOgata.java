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
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B3_KannaOgata extends Card {

    public SIGNI_B3_KannaOgata()
    {
        setImageSets("WX25-CP1-042");

        setOriginalName("尾刃カンナ");
        setAltNames("オガタカンナ Ogata Kanna");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが＜ブルアカ＞の場合、対戦相手は手札を１枚捨てる。\n" +
                "@U $T1：あなたのルリグアタックステップ開始時、このターンにあなたの青の＜ブルアカ＞のシグニがクラッシュした対戦相手のライフクロス１枚につき対戦相手は手札を１枚捨てる。" +
                "~{{E @[手札から＜ブルアカ＞のカードを１枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Ogata Kanna");

        setName("en_fan", "Kanna Ogata");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all of your SIGNI are <<Blue Archive>> SIGNI, your opponent discards 1 card from their hand.\n" +
                "@U $T1: At the beginning of your LRIG attack step, your opponent discards 1 card from their hand for each of their life cloth that was crushed by your blue <<Blue Archive>> SIGNI this turn." +
                "~{{E @[Discard 1 <<Blue Archive>> card from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "尾刃康娜");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的全部的精灵是<<ブルアカ>>的场合，对战对手把手牌1张舍弃。\n" +
                "@U $T1 :你的分身攻击步骤开始时，依据这个回合你的蓝色的<<ブルアカ>>精灵击溃对战对手的生命护甲的数量，每有1张对战对手就把手牌1张舍弃。\n" +
                "~{{E从手牌把<<ブルアカ>>牌1张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
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
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);

            EnterAbility enter = registerEnterAbility(new DiscardCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff);
            enter.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).getValidTargetsCount() == 0)
            {
                discard(getOpponent(), 1);
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_LRIG ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            discard(getOpponent(), GameLog.getTurnRecordsCount(event ->
                event.getId() == GameEventId.CRUSH && !isOwnCard(event.getCaller()) && isOwnCard(event.getSource()) &&
                CardType.isSIGNI(event.getSource().getCardReference().getType()) && event.getSource().getColor().matches(CardColor.BLUE) &&
                event.getSource().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE)
            ));
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -5000, ChronoDuration.turnEnd());
        }
    }
}
