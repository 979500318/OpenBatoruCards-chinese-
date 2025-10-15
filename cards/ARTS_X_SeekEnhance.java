package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockPlayerAbilityLimitUpper;

public final class ARTS_X_SeekEnhance extends Card {

    public ARTS_X_SeekEnhance()
    {
        setImageSets("WX24-P1-032", "PR-Di034");
        setLinkedImageSets(Token_LimitUpper.IMAGE_SET);

        setOriginalName("シーク・エンハンス");
        setAltNames("シークエンハンス Shiiku Enhansu");
        setDescription("jp",
                "あなたのデッキの上からカードを８枚見る。その中からカードを１枚まで手札に加え、残りをデッキに加えてシャッフルする。あなたのルリグゾーンに【リミットアッパー】１つを置く。"
        );

        setName("en", "Seek Enhance");
        setDescription("en",
                "Look at the top 8 cards of your deck. Add up to 1 card from among them to your hand, and shuffle the rest into your deck. Put 1 [[Limit Upper]] on your LRIG zone."
        );

		setName("zh_simplified", "探寻·突破");
        setDescription("zh_simplified", 
                "从你的牌组上面看8张牌。从中把牌1张最多加入手牌，剩下的加入牌组洗切。你的分身区放置[[界限高地]]1个。\n"
        );

        setType(CardType.ARTS);
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
            look(8);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            returnToDeck(getCardsInLooked(getOwner()), DeckPosition.TOP);
            shuffleDeck();

            attachPlayerAbility(getOwner(), new StockPlayerAbilityLimitUpper(), ChronoDuration.permanent());
        }
    }
}

