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
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K3_DullahanPhantomApparition extends Card {

    public SIGNI_K3_DullahanPhantomApparition()
    {
        setImageSets("WX25-P1-109");

        setOriginalName("幻怪　デュラハン");
        setAltNames("ゲンカイデュラハン Genkai Durrahan");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたがアーツを使用していた場合、対戦相手のシグニ１体を対象とし、%K を支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Dullahan, Phantom Apparition");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you used ARTS this turn, target 1 of your opponent's SIGNI, and you may pay %K. If you do, until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "幻怪 无头骑士");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这个回合你把必杀使用过的场合，对战对手的精灵1只作为对象，可以支付%K。这样做的场合，直到回合结束时为止，其的力量-8000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller())) > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
                {
                    gainPower(target, -8000, ChronoDuration.turnEnd());
                }
            }
        }
    }
}
