package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R2_MeerkatPhantomBeast extends Card {

    public SIGNI_R2_MeerkatPhantomBeast()
    {
        setImageSets("WXDi-P09-060");

        setOriginalName("幻獣　ミーアキャット");
        setAltNames("ゲンジュウミーアキャット Genjuu Miiakyatto");
        setDescription("jp",
                "@U $T1：あなたのパワー10000以上の＜地獣＞のシグニ１体がバニッシュされたとき、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたのシグニ１体を対象とし、ターン終了後まで、それのパワーをあなたの場にある＜地獣＞のシグニ１体につき＋2000する。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Meerkat, Phantom Terra Beast");
        setDescription("en",
                "@U $T1: When a <<Terra Beast>> SIGNI on your field with power 10000 or more is vanished, your opponent chooses a card from their Ener Zone and puts it into their trash.\n" +
                "@U: At the beginning of your attack phase, target SIGNI on your field gets +2000 power for each <<Terra Beast>> SIGNI on your field until end of turn." +
                "~#Vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Meerkat, Phantom Beast");
        setDescription("en_fan",
                "@U $T1: When 1 of your <<Earth Beast>> SIGNI with power 10000 or more is banished, your opponent chooses 1 card from their ener zone, and puts it into the trash.\n" +
                "@U: At the beginning of your attack phase, target 1 of your SIGNI, and until end of turn, it gets +2000 power for each <<Earth Beast>> SIGNI on your field." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "幻兽 狐獴");
        setDescription("zh_simplified", 
                "@U $T1 :当你的力量10000以上的<<地獣>>精灵1只被破坏时，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "@U :你的攻击阶段开始时，你的精灵1只作为对象，直到回合结束时为止，其的力量依据你的场上的<<地獣>>精灵的数量，每有1只就+2000。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) &&
                    caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.EARTH_BEAST) &&
                    caller.getIndexedInstance().getPower().getValue() >= 10000 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
            trash(cardIndex);
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            if(target != null) gainPower(target, 2000 * new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.EARTH_BEAST).getValidTargetsCount(), ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
