package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
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
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B2_TokiAsuma extends Card {

    public SIGNI_B2_TokiAsuma()
    {
        setImageSets("WXDi-CP02-078");

        setOriginalName("飛鳥馬トキ");
        setAltNames("アスマトキ Asuma Toki");
        setDescription("jp",
                "@A $T1 @[手札から＜ブルアカ＞のカードを１枚捨てる]@：次の対戦相手のターン終了時まで、このシグニのパワーを＋5000し、このシグニは@>@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、%Bを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－5000する。@@を得る。" +
                "~{{U：あなたのアタックフェイズ開始時、手札を１枚捨ててもよい。そうした場合、対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Asuma Toki");
        setDescription("en",
                "@A $T1 @[Discard a <<Blue Archive>> card]@: This SIGNI gets +5000 power and gains@>@U: Whenever this SIGNI attacks, you may pay %B. If you do, target SIGNI on your opponent's field gets --5000 power until end of turn.@@until the end of your opponent's next end phase.~{{U: At the beginning of your attack phase, you may discard a card. If you do, your opponent discards a card at random."
        );
        
        setName("en_fan", "Toki Asuma");
        setDescription("en_fan",
                "@A $T1 @[Discard 1 <<Blue Archive>> card from your hand]@: Until the end of your opponent's next turn, this SIGNI gets +5000 power, and it gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and you may pay %B. If you do, until end of turn, it gets --5000 power.@@" +
                "~{{U: At the beginning of your attack phase, you may discard 1 card from your hand. If you do, choose 1 card from your opponent's hand without looking, and your opponent discards it."
        );

		setName("zh_simplified", "飞鸟马时");
        setDescription("zh_simplified", 
                "@A $T1 从手牌把<<ブルアカ>>牌1张舍弃:直到下一个对战对手的回合结束时为止，这只精灵的力量+5000，这只精灵得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的精灵1只作为对象，可以支付%B。这样做的场合，直到回合结束时为止，其的力量-5000。@@\n" +
                "~{{U:你的攻击阶段开始时，可以把手牌1张舍弃。这样做的场合，不看对战对手的手牌选1张，舍弃。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
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

            ActionAbility act = registerActionAbility(new DiscardCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onActionEff()
        {
            gainPower(getCardIndex(), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
            
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLUE, 1)))
            {
                gainPower(target, -5000, ChronoDuration.turnEnd());
            }
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }
    }
}
