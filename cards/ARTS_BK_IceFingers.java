package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class ARTS_BK_IceFingers extends Card {

    public ARTS_BK_IceFingers()
    {
        setImageSets("WX25-P2-045");

        setOriginalName("アイス・フィンガーズ");
        setAltNames("アイスフィンガーズ Aisu Fingaazu");
        setDescription("jp",
                "あなたのデッキの上からカードを７枚見る。その中からスペルか＜電機＞のシグニを合計２枚まで公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。その後、この方法で手札に加えたカード１枚が青で、もう１枚が黒の場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。"
        );

        setName("en", "Ice Fingers");
        setDescription("en",
                "Look at the top 7 cards of your deck. Reveal up to 2 spells and/or <<Electric Machine>> SIGNI from among them, add them to your hand, and shuffle the rest and put them on the bottom of your deck. Then, if you added 1 blue and another black card to your hand this way, target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power."
        );

        setName("zh_simplified", "寒冰·再指");
        setDescription("zh_simplified", 
                "从你的牌组上面看7张牌。从中把魔法或<<电机>>精灵合计2张最多公开加入手牌，剩下的洗切放置到牌组最下面。然后，这个方法加入手牌的牌1张是蓝色且，另1张是黑色的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE, CardColor.BLACK);
        setCost(Cost.color(CardColor.BLUE, 0), Cost.color(CardColor.BLACK, 0));
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
            look(7);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().
                    or(new TargetFilter().spell(), new TargetFilter().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE).fromLooked()
                    ).fromLooked());
            boolean match = false;
            if(reveal(data) == 2)
            {
                CardDataColor color1 = data.get(0).getIndexedInstance().getColor();
                CardDataColor color2 = data.get(1).getIndexedInstance().getColor();
                
                match = (color1.matches(CardColor.BLUE) && color2.matches(CardColor.BLACK)) ||
                        (color1.matches(CardColor.BLACK) && color2.matches(CardColor.BLUE));
            }
            addToHand(data);

            int countReturned = returnToDeck(getCardsInLooked(getOwner()), DeckPosition.BOTTOM);
            shuffleDeck(countReturned, DeckPosition.BOTTOM);

            if(match)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -10000, ChronoDuration.turnEnd());
            }
        }
    }
}

