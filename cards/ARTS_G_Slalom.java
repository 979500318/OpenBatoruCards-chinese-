package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;

public final class ARTS_G_Slalom extends Card {

    public ARTS_G_Slalom()
    {
        setImageSets("WX25-P2-041");

        setOriginalName("回転競技");
        setAltNames("ウィングシュート Uingu Shuuto Wing Shoot");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中から＜遊具＞のシグニを１枚まで公開し手札に加え、＜遊具＞のシグニを１枚まで場に出し、残りを好きな順番でデッキの一番下に置く。その後、あなたの＜遊具＞のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、対戦相手のパワー10000以下のシグニ１体を対象とし、それをエナゾーンに置く。@@を得る。"
        );

        setName("en", "Slalom");
        setDescription("en",
                "Look at the top 5 cards of your deck. Reveal 1 <<Playground Equipment>> SIGNI from among them, add it to your hand, put 1 <<Playground Equipment>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. Then, target 1 of your <<Playground Equipment>> SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power 10000 or less, and put it into the ener zone."
        );

		setName("zh_simplified", "回转竞技");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把<<遊具>>精灵1张最多公开加入手牌，<<遊具>>精灵1张最多出场，剩下的任意顺序放置到牌组最下面。然后，你的<<遊具>>精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的力量10000以下的精灵1只作为对象，将其放置到能量区。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            look(5);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);

            cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).fromLooked().playable()).get();
            putOnField(cardIndex);

            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT)).get();
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI().withPower(0,10000)).get();
            getAbility().getSourceCardIndex().getIndexedInstance().putInEner(target);
        }
    }
}

