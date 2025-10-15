package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.DataTable;

public final class ARTS_X_SnowCharging extends Card {

    public ARTS_X_SnowCharging()
    {
        setImageSets("WX24-P4-037");

        setOriginalName("スノー・チャージング");
        setAltNames("スノーチャージング Sunoo Chaajingu");
        setDescription("jp",
                "あなたのデッキの上からカードを１０枚見る。その中からカードを２枚まで選び、残りをデッキに加えてシャッフルする。この方法で選んだカードを好きな枚数手札に加え、残りをエナゾーンに置く。"
        );

        setName("en", "Snow Charging");
        setDescription("en",
                "Look at the top 10 cards of your deck. Choose up to 2 cards from among them, and shuffle the rest into your deck. Add any number of cards chosen this way to your hand, and put the rest into the ener zone."
        );

		setName("zh_simplified", "冰雪·充能");
        setDescription("zh_simplified", 
                "从你的牌组上面看10张牌。从中选牌2张最多，剩下的加入牌组洗切。这个方法选的牌任意张数加入手牌，剩下的放置到能量区。\n"
        );

        setType(CardType.ARTS);
        setUseTiming(UseTiming.ATTACK);

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
            look(10);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter().own().fromLooked());
            if(data.get() != null)
            {
                DataTable<CardIndex> dataToHand = playerTargetCard(0,data.size(), new TargetFilter(TargetHint.HAND).own().fromLooked().match(data));
                int numToHand = addToHand(dataToHand);
                
                if(data.get().getIndexedInstance() != null)
                {
                    putInEner(data.andRemoveIf(cardIndex -> cardIndex.getLocation() == CardLocation.HAND));
                } else {
                    for(int i=0;i<data.size()-numToHand;i++) putInEner(CardLocation.LOOKED);
                }
            }
            
            returnToDeck(getCardsInLooked(getOwner()), DeckPosition.TOP);
            shuffleDeck();
        }
    }
}

