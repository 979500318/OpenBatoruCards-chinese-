package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class ARTS_G_InAClearVoice extends Card {

    public ARTS_G_InAClearVoice()
    {
        setImageSets("WX25-P1-041");

        setOriginalName("音吐朗朗");
        setAltNames("フォレストコンセート Foresuto Konseeto Forest Concert");
        setDescription("jp",
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それをエナゾーンに置く。あなたのデッキの上からカードを４枚見る。その中から緑のシグニ１枚と、そのシグニと共通するクラスを持たないシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "In a Clear Voice");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 8000 or less, and put it into their ener zone. Look at the top 4 cards of your deck. Reveal 1 green SIGNI and 1 SIGNI that doesn't share a common class with that SIGNI, add them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "音吐朗朗");
        setDescription("zh_simplified", 
                "对战对手的力量8000以下的精灵1只作为对象，将其放置到能量区。从你的牌组上面看4张牌。从中把绿色的精灵1张和，不持有与那张精灵共通类别的精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI().withPower(0,8000)).get();
            putInEner(target);
            
            look(4);
            
            DataTable<CardIndex> data = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.GREEN).fromLooked());
            if(data.get() != null)
            {
                TargetFilter filter = new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked();
                if(data.get().getIndexedInstance() != null) filter = filter.not(new TargetFilter().withClass(data.get().getIndexedInstance().getSIGNIClass()));
                data.add(playerTargetCard(0,1, filter).get());
                
                reveal(data);
                addToHand(data);
            }
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}

