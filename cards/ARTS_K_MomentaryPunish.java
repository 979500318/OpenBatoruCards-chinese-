package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.DataTable;

public final class ARTS_K_MomentaryPunish extends Card {

    public ARTS_K_MomentaryPunish()
    {
        setImageSets("WX24-D5-06");

        setOriginalName("モーメンタリー・パニッシュ");
        setAltNames("モーメンタリーパニッシュ Moomentarii Panisshu");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。あなたのデッキの上からカードを４枚見る。その中から黒のカードを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Momentary Punish");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power. Look at the top 4 cards of your deck. Reveal up to 2 black cards from among them, and add them to your hand, and put the rest on the bottom of your deck in any order."
        );

        setName("zh_simplified", "刹那谎言·惩戒");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。从你的牌组上面看4张牌。从中把黑色的牌2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。"
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());

            look(4);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().withColor(CardColor.BLACK).fromLooked());
            reveal(data);
            addToHand(data);

            returnToDeckOrdered(CardLocation.LOOKED, DeckPosition.BOTTOM);
        }
    }
}

