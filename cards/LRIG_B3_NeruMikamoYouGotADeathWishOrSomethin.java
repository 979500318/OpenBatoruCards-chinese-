package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B3_NeruMikamoYouGotADeathWishOrSomethin extends Card {

    public LRIG_B3_NeruMikamoYouGotADeathWishOrSomethin()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WXDi-CP02-008");

        setOriginalName("美甘ネル[あぁ？ぶっ殺されてえか？]");
        setAltNames("ミカモネルアァブッコロサレテエカ Mikamo Neru Aa Bukkorosareteeka");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたが手札を２枚以上捨てていた場合、対戦相手のシグニ１体を対象とし、%Bを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－10000する。\n" +
                "@A $G1 %B0：カードを２枚引く。対戦相手の手札を１枚見ないで選び、捨てさせる。" +
                "~{{A $T1 %X：カードを２枚引き、手札を１枚捨てる。"
        );

        setName("en", "Mikamo Neru [You Got a Death Wish or Somethin'?]");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have discarded two or more cards this turn, you may pay %B. If you do, target SIGNI on your opponent's field gets --10000 power until end of turn.\n@A $G1 %B0: Draw two cards. Your opponent discards a card at random.~{{A $T1 %X: Draw two cards and discard a card."
        );
        
        setName("en_fan", "Neru Mikamo [You Got a Death Wish or Somethin'?]");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if you discarded 2 or more cards from your hand this turn, target 1 of your opponent's SIGNI, and you may pay %B. If you do, until end of turn, it gets --10000 power.\n" +
                "@A $G1 %B0: Draw 2 cards. Choose 1 card from your opponent's hand without looking, and your opponent discards it." +
                "~{{A $T1 %X: Draw 2 cards, and discard 1 card from your hand."
        );

		setName("zh_simplified", "美甘妮露[啊~？活腻了吧你？]");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这个回合你把手牌2张以上舍弃过的场合，对战对手的精灵1只作为对象，可以支付%B。这样做的场合，直到回合结束时为止，其的力量-10000。\n" +
                "@A $G1 %B0:抽2张牌。不看对战对手的手牌选1张，舍弃。\n" +
                "~{{A$T1 %X:抽2张牌，手牌1张舍弃。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.NERU);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.GAME, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.colorless(1)), this::onActionEff2);
            act2.setUseLimit(UseLimit.TURN, 1);
            act2.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.DISCARD && isOwnCard(event.getCaller())) >= 2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLUE, 1)))
                {
                    gainPower(target, -10000, ChronoDuration.turnEnd());
                }
            }
        }

        private void onActionEff1()
        {
            draw(2);
            
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }

        private void onActionEff2()
        {
            draw(2);
            discard(1);
        }
    }
}
