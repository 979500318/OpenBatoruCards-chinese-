package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
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

public final class SIGNI_G1_MichiruChidori extends Card {

    public SIGNI_G1_MichiruChidori()
    {
        setImageSets("WXDi-CP02-086");

        setOriginalName("千鳥ミチル");
        setAltNames("チドリミチル Chidori Michiru");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにコストか効果によってカードが１枚以上あなたのエナゾーンに置かれていた場合、対戦相手のパワー5000以下のシグニ１体を対象とし、あなたのエナゾーンから＜ブルアカ＞のカード１枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。" +
                "~{{U $T1：このシグニが対戦相手のライフクロス１枚をクラッシュしたとき、【エナチャージ１】をする。"
        );

        setName("en", "Chidori Michiru");
        setDescription("en",
                "@U: At the beginning of your attack phase, if one or more cards were put into your Ener Zone by a cost or an effect this turn, you may put a <<Blue Archive>> card from your Ener Zone into your trash. If you do, vanish target SIGNI on your opponent's field with power 5000 or less.~{{U $T1: When this SIGNI crushes one of your opponent's Life Cloth, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Michiru Chidori");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if 1 or more cards were put into your ener zone by a cost or effect this turn, target 1 of your opponent's SIGNI with power 5000 or less, and you may put 1 <<Blue Archive>> card from your ener zone into the trash. If you do, banish it." +
                "~{{U $T1: When this SIGNI crushes 1 of your opponent's life cloth, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "千鸟满");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这个回合因为费用或效果把牌1张以上放置到你的能量区的场合，对战对手的力量5000以下的精灵1只作为对象，可以从你的能量区把<<ブルアカ>>牌1张放置到废弃区。这样做的场合，将其破坏。\n" +
                "~{{U$T1 :当这只精灵把对战对手的生命护甲1张击溃时，[[能量填充1]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
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
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ENER && isOwnCard(event.getCaller()) && event.getSourceAbility() != null) >= 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
                
                if(target != null)
                {
                    CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()).get();
                    
                    if(trash(cardIndex))
                    {
                        banish(target);
                    }
                }
            }
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
