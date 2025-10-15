package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_K2_ZweiHanareDissona extends Card {

    public SIGNI_K2_ZweiHanareDissona()
    {
        setImageSets("WXDi-P12-086", "SPDi01-89");

        setOriginalName("ツヴァイ＝ハナレ//ディソナ");
        setAltNames("ツヴァイハナレディソナ Tsuvai Hanare Disona");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをあなたの場にある他の#Sのシグニ１体につき－1000する。"
        );

        setName("en", "Hanare//Dissona Type: Drei");
        setDescription("en",
                "@U: At the beginning of your attack phase, target SIGNI on your opponent's field gets --1000 power for each other #S SIGNI on your field until end of turn. "
        );
        
        setName("en_fan", "Zwei-Hanare//Dissona");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power for each of your other #S @[Dissona]@ SIGNI."
        );

		setName("zh_simplified", "ZWEI=离//失调");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据你的场上的其他的#S的精灵的数量，每有1只就-1000。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, -1000 * new TargetFilter().own().SIGNI().dissona().except(getCardIndex()).getValidTargetsCount(), ChronoDuration.turnEnd());
        }
    }
}
