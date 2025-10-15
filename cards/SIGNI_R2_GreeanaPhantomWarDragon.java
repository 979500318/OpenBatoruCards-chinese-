package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_GreeanaPhantomWarDragon extends Card {

    public SIGNI_R2_GreeanaPhantomWarDragon()
    {
        setImageSets("WX24-P2-071");

        setOriginalName("幻闘竜　グリアナ");
        setAltNames("ゲントウリュウグリアナ Gentouryuu Guriana");
        setDescription("jp",
                "@U: あなたのアタックフェイズ開始時、あなたの場に他の＜龍獣＞のシグニがある場合、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。" +
                "~#：対戦相手のパワー10000以下のシグニ１体を対象とし、対戦相手が%X %X %Xを支払わないかぎり、それをバニッシュする。"
        );

        setName("en", "Greeana, Phantom War Dragon");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is another <<Dragon Beast>> SIGNI on your field, target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash." +
                "~#Target 1 of your opponent's SIGNI with power 10000 or less, and banish it unless your opponent pays %X %X %X."
        );

		setName("zh_simplified", "幻斗龙 绿鬣蜥");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有其他的<<龍獣>>精灵的场合，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。" +
                "~#对战对手的力量10000以下的精灵1只作为对象，如果对战对手不把%X %X %X:支付，那么将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DRAGON_BEAST).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner()).get();
                trash(target);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            if(target != null && !payEner(getOpponent(), Cost.colorless(3)))
            {
                banish(target);
            }
        }
    }
}
