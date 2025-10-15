package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class ARTS_R_RedDesignsForFieryChariots extends Card {

    public ARTS_R_RedDesignsForFieryChariots()
    {
        setImageSets("WX25-P2-037");

        setOriginalName("赤計火車");
        setAltNames("セッケイカシャ Sekkei Kasha");
        setDescription("jp",
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。あなたのデッキの上からカードを５枚見る。その中からスペルか＜電機＞のシグニを合計２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Red Designs for Fiery Chariots");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 8000 or less, and banish it. Look at the top 5 cards of your deck. Reveal up to 2 spells and/or <<Electric Machine>> SIGNI from among them, add them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "赤计火车");
        setDescription("zh_simplified", 
                "对战对手的力量8000以下的精灵1只作为对象，将其破坏。从你的牌组上面看5张牌。从中把魔法或<<電機>>精灵合计2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);

            look(5);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().
                or(new TargetFilter().spell(), new TargetFilter().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE).fromLooked()
            ).fromLooked());
            reveal(data);
            addToHand(data);

            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}

