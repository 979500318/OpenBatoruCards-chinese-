package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.DataTable;

public final class ARTS_G_FallenIntoTrees extends Card {

    public ARTS_G_FallenIntoTrees()
    {
        setImageSets("WX24-D4-06");

        setOriginalName("樹木堕絡");
        setAltNames("エンタングル Entanguru Entangle");
        setDescription("jp",
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それをエナゾーンに置く。あなたのデッキの上からカードを４枚見る。その中から緑のカードを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Fallen into Trees");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 8000 or less, and put it into the ener zone. Look at the top 4 cards of your deck. Reveal up to 2 green cards from among them, and add them to your hand, and put the rest on the bottom of your deck in any order."
        );

        setName("zh_simplified", "树木堕络");
        setDescription("zh_simplified", 
                "对战对手的力量8000以下的精灵1只作为对象，将其放置到能量区。从你的牌组上面看4张牌。从中把绿色的牌2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。"
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI().withPower(0,8000)).get();
            putInEner(target);

            look(4);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().withColor(CardColor.GREEN).fromLooked());
            reveal(data);
            addToHand(data);

            returnToDeckOrdered(CardLocation.LOOKED, DeckPosition.BOTTOM);
        }
    }
}

