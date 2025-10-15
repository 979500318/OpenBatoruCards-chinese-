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
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K2_AkariWanibuchi extends Card {

    public SIGNI_K2_AkariWanibuchi()
    {
        setImageSets("WXDi-CP02-098");

        setOriginalName("鰐渕アカリ");
        setAltNames("ワニブチアカリ Wanibuchi Akari");
        setDescription("jp",
                "@E @[手札から＜ブルアカ＞のカードを２枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。" +
                "~{{U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );

        setName("en", "Wanibuchi Akari");
        setDescription("en",
                "@E @[Discard two <<Blue Archive>> cards]@: Target SIGNI on your opponent's field gets --10000 power until end of turn.\n~{{U: At the beginning of your attack phase, target SIGNI on your opponent's field gets --2000 power until end of turn."
        );
        
        setName("en_fan", "Akari Wanibuchi");
        setDescription("en_fan",
                "@E @[Discard 2 <<Blue Archive>> cards from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power." +
                "~{{U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "鳄渊明里");
        setDescription("zh_simplified", 
                "@E 从手牌把<<ブルアカ>>牌2张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。\n" +
                "~{{U:你的攻击阶段开始时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new DiscardCost(2, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000, ChronoDuration.turnEnd());
        }
    }
}
