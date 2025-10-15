package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.DataTable;

public final class ARTS_W_RococoadoBoundary extends Card {

    public ARTS_W_RococoadoBoundary()
    {
        setImageSets("WX24-D1-06");

        setOriginalName("ロココアドバウンダリー");
        setAltNames("ロココアド・バウンダリー Rokokoado Baundari");
        setDescription("jp",
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それを手札に戻す。あなたのデッキの上からカードを４枚見る。その中から白のカードを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Rococoado Boundary");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 8000 or less, and return it to their hand. Look at the top 4 cards of your deck. Reveal up to 2 white cards from among them, and add them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "洛可可告终·界限");
        setDescription("zh_simplified", 
                "对战对手的力量8000以下的精灵1只作为对象，将其返回手牌。从你的牌组上面看4张牌。从中把白色的牌2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,8000)).get();
            addToHand(target);
            
            look(4);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().withColor(CardColor.WHITE).fromLooked());
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}

