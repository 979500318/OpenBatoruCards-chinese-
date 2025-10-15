package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_CodeArtBAlloonPump extends Card {

    public SIGNI_R2_CodeArtBAlloonPump()
    {
        setImageSets("WX25-P2-075");

        setOriginalName("コードアート　Bルーンマシン");
        setAltNames("コードアートビールーンマシン Koodo Aato Bii Ruun Mashin Balloon Machine");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、以下の２つから１つを選ぶ。\n" +
                "$$1このターンにあなたが赤のスペルを使用していた場合、対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2このシグニが覚醒状態の場合、対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Code Art B Alloon Pump");
        setDescription("en",
                "@U: At the beginning of your attack phase, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If you used a red spell this turn, target 1 of your opponent's SIGNI with power 3000 or less, and banish it.\n" +
                "$$2 If this SIGNI is awakened, target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );

		setName("zh_simplified", "必杀代号 吹气球机");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从以下的2种选1种。\n" +
                "$$1 这个回合你把红色的魔法使用过的场合，对战对手的力量3000以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 这只精灵在觉醒状态的场合，对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
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
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(playerChoiceMode() == 1)
            {
                if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_SPELL && isOwnCard(event.getCaller()) && event.getCaller().getColor().matches(CardColor.RED)) > 0)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
                    banish(target);
                }
            } else if(isState(CardStateFlag.AWAKENED))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
                banish(target);
            }
        }
    }
}
