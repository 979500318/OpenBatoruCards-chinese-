package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class ARTS_G_JumpingForJoy extends Card {

    public ARTS_G_JumpingForJoy()
    {
        setImageSets("WX24-P1-025");

        setOriginalName("欣喜雀躍");
        setAltNames("リフレッシュダンス Rifuresshu Densu Refresh Dance");
        setDescription("jp",
                "あなたのトラッシュにあるすべてのカードをデッキに加えてシャッフルする。あなたのデッキの上からカードを３枚見る。その中からカードを好きな枚数手札に加え、残りをエナゾーンに置く。"
        );

        setName("en", "Jumping for Joy");
        setDescription("en",
                "Shuffle all cards from your trash into your deck. Look at the top 3 cards of your deck, and add any number of them to your hand, and put the rest in the ener zone."
        );

		setName("zh_simplified", "欣喜雀跃");
        setDescription("zh_simplified", 
                "你的废弃区的全部的牌加入牌组洗切。从你的牌组上面看3张牌。从中把牌任意张数加入手牌，剩下的放置到能量区。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
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
            returnToDeck(getCardsInTrash(getOwner()), DeckPosition.TOP);
            shuffleDeck();
            
            look(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().fromLooked());
            addToHand(data);
            
            putInEner(getCardsInLooked(getOwner()));
        }
    }
}

