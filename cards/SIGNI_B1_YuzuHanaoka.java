package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
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
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_YuzuHanaoka extends Card {

    public SIGNI_B1_YuzuHanaoka()
    {
        setImageSets("WXDi-CP02-077");

        setOriginalName("花岡ユズ");
        setAltNames("ハナオカユズ Hanaoka Yuzu");
        setDescription("jp",
                "@U $T2：あなたのターンの間、コストか効果１つによって、あなたが手札から＜ブルアカ＞のカードを１枚以上捨てたとき、%Xを支払ってもよい。そうした場合、カードを１枚引く。" +
                "~{{U：あなたのアタックフェイズ開始時、手札を１枚捨ててもよい。そうした場合、対戦相手の手札を１枚見ないで選び、捨てさせる。@@" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Hanaoka Yuzu");
        setDescription("en",
                "@U $T2: During your turn, whenever you discard one or more <<Blue Archive>> cards by a cost or an effect, you may pay %X. If you do, draw a card.~{{U: At the beginning of your attack phase, you may discard a card. If you do, your opponent discards a card at random.@@" +
                "~#Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand or put it onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Yuzu Hanaoka");
        setDescription("en_fan",
                "@U $T2: During your turn, whenever you discard 1 or more <<Blue Archive>> cards from your hand by a cost or a single effect, you may pay %X. If you do, draw 1 card." +
                "~{{U: At the beginning of your attack phase, you may discard 1 card from your hand. If you do, choose 1 card from your opponent's hand without looking, and your opponent discards it.@@" +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand, or put it onto the field, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "花冈柚子");
        setDescription("zh_simplified", 
                "@U $T2 :你的回合期间，当因为费用或效果1个，你从手牌把<<ブルアカ>>牌1张以上舍弃时，可以支付%X。这样做的场合，抽1张牌。\n" +
                "~{{U:你的攻击阶段开始时，可以把手牌1张舍弃。这样做的场合，不看对战对手的手牌选1张，舍弃。@@" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 2);
            auto1.getFlags().addValue(AbilityFlag.ACTIVE_ONCE_PER_EFFECT);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && getEvent().getSourceAbility() != null && getEvent().isAtOnce(1) &&
                   caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(payEner(Cost.colorless(1)))
            {
                draw(1);
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }
        
        private void onLifeBurstEff()
        {
            look(3);

            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked()).get();
            reveal(cardIndex);

            if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
            {
                addToHand(cardIndex);
            } else {
                putOnField(cardIndex);
            }

            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
