package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class ARTS_B_StationeryOne extends Card {

    public ARTS_B_StationeryOne()
    {
        setImageSets("WX24-P2-035");

        setOriginalName("ステーショナリー・ワン");
        setAltNames("ステーショナリーワン Suteeshanerii Wan");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中から＜遊具＞のシグニを１枚まで公開し手札に加え、＜遊具＞のシグニを１枚まで場に出し、残りを好きな順番でデッキの一番下に置く。その後、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをこの効果で場に出たシグニのレベル１につき－5000する。"
        );

        setName("en", "Stationery One");
        setDescription("en",
                "Look at the top 5 cards of your deck. Reveal up to 1 <<Playground Equipment>> SIGNI from among them, add it to your hand, and put up to 1 <<Playground Equipment>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. Then, target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power for each level of the SIGNI put onto the field this way."
        );

		setName("zh_simplified", "文具·壹");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把<<遊具>>精灵1张最多公开加入手牌，<<遊具>>精灵1张最多出场，剩下的任意顺序放置到牌组最下面。然后，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据这个效果出场的精灵的等级的数量，每有1级就-5000。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
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
            
            CardIndex cardIndexField = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).fromLooked().playable()).get();
            boolean isPutOnField = putOnField(cardIndexField);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            if(isPutOnField)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -5000 * cardIndexField.getIndexedInstance().getLevel().getValue(), ChronoDuration.turnEnd());
            }
        }
    }
}
