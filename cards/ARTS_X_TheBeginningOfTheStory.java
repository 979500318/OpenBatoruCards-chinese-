package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardSIGNIClass;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.DataTable;

public final class ARTS_X_TheBeginningOfTheStory extends Card {

    public ARTS_X_TheBeginningOfTheStory()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-035");

        setOriginalName("物語の幕開け");
        setAltNames("モノガタリノマクアケ Monogatari no Makuake");
        setDescription("jp",
                "あなたのデッキの上からカードを４枚見る。その中から＜ブルアカ＞のカードを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。好きな生徒１人との絆を獲得する。"
        );

        setName("en", "The Beginning of the Story");
        setDescription("en",
                "Look at the top 4 cards of your deck. Reveal up to 2 <<Blue Archive>> cards from among them, add them to your hand, and put the rest on the bottom of your deck in any order. Gain a bond with a student of your choice."
        );

		setName("zh_simplified", "物语的开幕");
        setDescription("zh_simplified", 
                "从你的牌组上面看4张牌。从中把<<ブルアカ>>牌2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。获得与任意学生1人的羁绊。\n"
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

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromLooked());
            reveal(data);
            addToHand(data);

            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }

            playerChoiceBond();
        }
    }
}
