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

public final class ARTS_X_FamiliarEnhance extends Card {

    public ARTS_X_FamiliarEnhance()
    {
        setImageSets("WX25-P2-046");
        setLinkedImageSets(Token_LimitUpper.IMAGE_SET);

        setOriginalName("ファミリア・エンハンス");
        setAltNames("ファミリアエンハンス Famiria Enhansu");
        setDescription("jp",
                "あなたのデッキの上からカードを４枚見る。その中からあなたのセンタールリグと共通する色を持つカードを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。あなたのルリグゾーンに【リミットアッパー】１つを置く。"
        );

        setName("en", "Familiar Enhance");
        setDescription("en",
                "Look at the top 4 cards of your deck. Reveal up to 2 cards that share a common color with your center LRIG from among them, add them to your hand, and put the rest on the bottom of your deck in any order. Put 1 [[Limit Upper]] on your LRIG zone."
        );

		setName("zh_simplified", "环宇·突破");
        setDescription("zh_simplified", 
                "从你的牌组上面看4张牌。从中把持有与你的核心分身共通颜色的牌2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。你的分身区放置[[界限高地]]1个。\n"
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
            look(4);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromLooked());
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            attachPlayerAbility(getOwner(), new StockPlayerAbilityLimitUpper(), ChronoDuration.permanent());
        }
    }
}

