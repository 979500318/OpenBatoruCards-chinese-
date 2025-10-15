package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class ARTS_X_RestartTemple extends Card {

    public ARTS_X_RestartTemple()
    {
        setImageSets("WX25-P1-047");

        setOriginalName("リスタート・テンプル");
        setAltNames("リスタートテンプル Risutaato Tenperu");
        setDescription("jp",
                "手札をすべて捨て、カードを５枚引く。"
        );

        setName("en", "Restart Temple");
        setDescription("en",
                "Discard all cards from your hand, and draw 5 cards."
        );

		setName("zh_simplified", "再开·神殿");
        setDescription("zh_simplified", 
                "手牌全部舍弃，抽5张牌。\n"
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
            discard(getCardsInHand(getOwner()));
            draw(5);
        }
    }
}

