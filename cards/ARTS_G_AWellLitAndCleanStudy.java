package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class ARTS_G_AWellLitAndCleanStudy extends Card {

    public ARTS_G_AWellLitAndCleanStudy()
    {
        setImageSets("WX24-P2-038");

        setOriginalName("明窓浄机");
        setAltNames("クリーンナップ Kuriin Nappu Clean Up");
        setDescription("jp",
                "あなたのトラッシュにあるすべてのカードをデッキに加えてシャッフルし、デッキの一番上のカードをライフクロスに加える。"
        );

        setName("en", "A Well-Lit and Clean Study");
        setDescription("en",
                "Shuffle all cards from your trash into your deck, and add the top card of your deck to life cloth."
        );

        setName("zh_simplified", "明窓净机");
        setDescription("zh_simplified", 
                "你的废弃区的全部的牌加入牌组洗切，牌组最上面的牌加入生命护甲。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(3));
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
            returnToDeck(getCardsInTrash(getOwner()), DeckPosition.TOP);
            shuffleDeck();
            
            addToLifeCloth(1);
        }
    }
}
