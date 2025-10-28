package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.DataTable;

public final class ARTS_K_NewYearsAperitif extends Card {

    public ARTS_K_NewYearsAperitif()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-031");

        setOriginalName("喰積の前にいささか");
        setAltNames("クイツミノマエニイササカ Kuitsumi no Mae ni Isasaka");
        setDescription("jp",
                "あなたのデッキの上からカードを５枚見る。その中から＜ブルアカ＞のカードを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。その後、この方法で黒の＜ブルアカ＞のカードを１枚以上手札に加えた場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。"
        );

        setName("en", "News Year's Aperitif");
        setDescription("en",
                "Look at the top 5 cards of your deck. Reveal up to 2 <<Blue Archive>> cards from among them, add them to your hand, and put the rest on the bottom of your deck in any order. Then, if you added 1 or more black <<Blue Archive>> cards to your hand this way, target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power."
        );

        setName("zh_simplified", "在年菜之前稍作比试");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把<<蔚蓝档案>>牌2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。然后，这个方法把黑色的<<蔚蓝档案>>牌1张以上加入手牌的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
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
            look(5);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromLooked());
            reveal(data);
            boolean match = data.get() != null && data.stream().anyMatch(cardIndex -> cardIndex.getIndexedInstance().getColor().matches(CardColor.BLACK));
            addToHand(data);

            returnToDeckOrdered(CardLocation.LOOKED, DeckPosition.BOTTOM);

            if(match)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -10000, ChronoDuration.turnEnd());
            }
        }
    }
}
