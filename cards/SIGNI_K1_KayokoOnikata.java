package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K1_KayokoOnikata extends Card {

    public SIGNI_K1_KayokoOnikata()
    {
        setImageSets("WXDi-CP02-094");

        setOriginalName("鬼方カヨコ");
        setAltNames("オニカタカヨコ Onikata Kayoko");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたのデッキから＜ブルアカ＞のカードが１枚以上トラッシュに置かれていた場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。" +
                "~{{U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );

        setName("en", "Onikata Kayoko");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if one or more <<Blue Archive>> cards were put from your deck into your trash this turn, target SIGNI on your opponent's field gets --2000 power until end of turn.\n~{{U: At the beginning of your attack phase, target SIGNI on your opponent's field gets --2000 power until end of turn."
        );
        
        setName("en_fan", "Kayoko Onikata");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if 1 or more <<Blue Archive>> cards were put from your deck into the trash this turn, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power." +
                "~{{U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "鬼方佳代子");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合从你的牌组把<<ブルアカ>>牌1张以上放置到废弃区的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n" +
                "~{{U:你的攻击阶段开始时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onAutoEff1()
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.TRASH && isOwnCard(event.getCaller()) && event.getCaller().isEffectivelyAtLocation(CardLocation.DECK_MAIN) &&
                                                    getEvent().getCaller().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE)) >= 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -2000, ChronoDuration.turnEnd());
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
    }
}
