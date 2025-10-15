package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class ARTS_G_SpiralThorn extends Card {

    public ARTS_G_SpiralThorn()
    {
        setImageSets("WX24-P2-037");

        setOriginalName("蔓巻発条");
        setAltNames("スパイラルソーン Supairaru Soon");
        setDescription("jp",
                "対戦相手のパワー10000以下のシグニ１体を対象とし、それをエナゾーンに置く。あなたのデッキの上からカードを５枚見る。その中から＜植物＞のシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Spiral Thorn");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 10000 or less, and put it into the ener zone. Look at the top 5 cards of your deck. Reveal up to 2 <<Plant>> SIGNI from among them, and add them to your hand, and put the rest on the botom of your deck in any order."
        );

		setName("zh_simplified", "蔓卷发条");
        setDescription("zh_simplified", 
                "对战对手的力量10000以下的精灵1只作为对象，将其放置到能量区。从你的牌组上面看5张牌。从中把<<植物>>精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI().withPower(0,10000)).get();
            putInEner(target);
            
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.PLANT).fromLooked());
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
