package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B3_PlatinumNaturalSourcePrincess extends Card {

    public SIGNI_B3_PlatinumNaturalSourcePrincess()
    {
        setImageSets("WX25-P2-057");

        setOriginalName("羅原姫　Pt");
        setAltNames("ラゲンヒメプラチナ Ragenhime Purachina");
        setDescription("jp",
                "@U $T1：あなたの青のシグニ１体がバニッシュされたとき、以下の２つから１つを選ぶ。\n" +
                "$$1カードを１枚引く。\n" +
                "$$2あなたの手札からレベル１のシグニ１枚をダウン状態で場に出す。そのシグニの@E能力は発動しない。\n" +
                "@U：対戦相手のターン終了時、このシグニをバニッシュしてもよい。そうした場合、対戦相手は手札を１枚捨てる。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Platinum, Natural Source Princess");
        setDescription("en",
                "@U $T1: When 1 of your blue SIGNI is banished, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Draw 1 card.\n" +
                "$$2 Put 1 level 1 SIGNI from your hand onto the field downed. That SIGNI's @E abilities don't activate.\n" +
                "@U: At the end of your opponent's turn, you may banish this SIGNI. If you do, your opponent discards 1 card from their hand." +
                "~#Target 1 of your opponent's upped SIGNI, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "罗原姬 Pt");
        setDescription("zh_simplified", 
                "@U $T1 :当你的蓝色的精灵1只被破坏时，从以下的2种选1种。\n" +
                "$$1 抽1张牌。\n" +
                "$$2 从你的手牌把等级1的精灵1张以横置状态出场。那只精灵的@E能力不能发动。\n" +
                "@U :对战对手的回合结束时，可以把这只精灵破坏。这样做的场合，对战对手把手牌1张舍弃。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            return isOwnCard(caller) && caller.getIndexedInstance().getColor().matches(CardColor.BLUE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(playerChoiceMode() == 1)
            {
                draw(1);
            } else {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(1).fromHand().playable()).get();
                putOnField(cardIndex, Enter.DOWNED | Enter.DONT_ACTIVATE);
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(getCardIndex().isSIGNIOnField() && playerChoiceActivate())
            {
                banish(getCardIndex());
                
                discard(getOpponent(), 1);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().upped()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
