package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class ARTS_R_GleamingGemsAndSparklingStones extends Card {

    public ARTS_R_GleamingGemsAndSparklingStones()
    {
        setImageSets("WX24-P1-020");

        setOriginalName("玉石混煌");
        setAltNames("ギョクセキコンコウ Gyokuseki Konkou");
        setDescription("jp",
                "対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。あなたのデッキの上からカードを５枚見る。その中から＜宝石＞のシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Gleaming Gems and Sparkling Stones");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 10000 or less, and banish it. Look at the top 5 cards of your deck, reveal up to 2 <<Gem>> SIGNI from among them, and add them to your hand, and put the rest on the bottom of your deck in any order."
        );

        setName("zh_simplified", "玉石混煌");
        setDescription("zh_simplified", 
                "对战对手的力量10000以下的精灵1只作为对象，将其破坏。从你的牌组上面看5张牌。从中把<<宝石>>精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);

            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.GEM).fromLooked());
            reveal(data);
            addToHand(data);
            
            returnToDeckOrdered(CardLocation.LOOKED, DeckPosition.BOTTOM);
        }
    }
}

