package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G2_ChiseWaraku extends Card {

    public SIGNI_G2_ChiseWaraku()
    {
        setImageSets("WXDi-CP02-090");

        setOriginalName("和楽チセ");
        setAltNames("ワラクチセ Waraku Chise");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのエナゾーンに＜ブルアカ＞のカードが３枚以上ある場合、対戦相手のパワー10000以下のシグニ１体を対象とし、%G %Xを支払ってもよい。そうした場合、それをバニッシュする。" +
                "~{{U $T1：このシグニが対戦相手のライフクロス１枚をクラッシュしたとき、【エナチャージ１】をする。"
        );

        setName("en", "Waraku Chise");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are three or more <<Blue Archive>> cards in your Ener Zone, you may pay %G %X. If you do, vanish target SIGNI on your opponent's field with power 10000 or less.~{{U $T1: When this SIGNI crushes one of your opponent's Life Cloth, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Chise Waraku");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 3 or more <<Blue Archive>> cards in your ener zone, target 1 of your opponent's SIGNI with power 10000 or less, and you may pay %G %X. If you do, banish it." +
                "~{{U $T1: When this SIGNI crushes 1 of your opponent's life cloth, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "和乐千世");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的能量区的<<ブルアカ>>牌在3张以上的场合，对战对手的力量10000以下的精灵1只作为对象，可以支付%G%X。这样做的场合，将其破坏。\n" +
                "~{{U$T1 :当这只精灵把对战对手的生命护甲1张击溃时，[[能量填充1]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
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
            if(new TargetFilter().own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner().getValidTargetsCount() >= 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
                
                if(target != null && payEner(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)))
                {
                    banish(target);
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
