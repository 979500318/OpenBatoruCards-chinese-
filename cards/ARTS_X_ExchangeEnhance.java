package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.stock.StockPlayerAbilityLimitUpper;

public final class ARTS_X_ExchangeEnhance extends Card {

    public ARTS_X_ExchangeEnhance()
    {
        setImageSets("WX25-P1-046");
        setLinkedImageSets(Token_LimitUpper.IMAGE_SET);

        setOriginalName("エクスチェンジ・エンハンス");
        setAltNames("エクスチェンジエンハンス Ekusuchenji Enhansu");
        setDescription("jp",
                "あなたの手札からカードを５枚まで好きな順番でデッキの一番下に置く。この方法でデッキに移動したカードの枚数に１を加えた枚数のカードを引く。あなたのルリグゾーンに【リミットアッパー】１つを置く。"
        );

        setName("en", "Exchange Enhance");
        setDescription("en",
                "Put up to 5 cards from your hand on the bottom of your deck in any order. Draw a number of cards equal to the number of cards put into the deck this way plus 1. Put 1 [[Limit Upper]] on your LRIG zone."
        );

		setName("zh_simplified", "交辉·突破");
        setDescription("zh_simplified", 
                "从你的手牌把牌5张最多任意顺序放置到牌组最下面。抽这个方法往牌组移动的牌的张数加1的张数的牌。你的分身区放置[[界限高地]]1个。\n"
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
            DataTable<CardIndex> data = playerTargetCard(0,5, new TargetFilter(TargetHint.BOTTOM).own().fromHand());
            int count = returnToDeck(data, DeckPosition.BOTTOM);
            draw(count+1);
            
            attachPlayerAbility(getOwner(), new StockPlayerAbilityLimitUpper(), ChronoDuration.permanent());
        }
    }
}

