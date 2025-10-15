package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K2_CodeArtMegatron extends Card {

    public SIGNI_K2_CodeArtMegatron()
    {
        setImageSets("PR-Di039");

        setOriginalName("コードアート　メガトロン");
        setAltNames("コードアートメガトロン Koodo Aato Megatoron");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このシグニが覚醒状態の場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。\n" +
                "@A %X：このシグニは覚醒する。"
        );

        setName("en", "Code Art Megatron");
        setDescription("en",
                "@U: At the beginning of your attack phase, if this SIGNI is awakened, target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power.\n" +
                "@A %X: This SIGNI awakens."
        );

		setName("zh_simplified", "必杀代号 威震天");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这只精灵在觉醒状态的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n" +
                "@A %X:这只精灵觉醒。（精灵觉醒后在场上保持觉醒状态）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.colorless(1)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(isState(CardStateFlag.AWAKENED))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -3000, ChronoDuration.turnEnd());
            }
        }

        private ConditionState onActionEffCond()
        {
            return !isState(CardStateFlag.AWAKENED) ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff()
        {
            getCardStateFlags().addValue(CardStateFlag.AWAKENED);
        }
    }
}
