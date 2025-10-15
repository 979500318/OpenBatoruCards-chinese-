package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B1_ThoriumDioxideNaturalSource extends Card {

    public SIGNI_B1_ThoriumDioxideNaturalSource()
    {
        setImageSets("WXDi-P11-065");

        setOriginalName("羅原　ＴｈＯ２");
        setAltNames("ラゲンサンカトリウム Ragen Senka Toriumu");
        setDescription("jp",
                "@E @[手札を１枚捨てる]@：あなたのデッキの上からカードを３枚見る。その中から＜原子＞のシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "ThO2, Natural Element");
        setDescription("en",
                "@E @[Discard a card]@: Look at the top three cards of your deck. Reveal an <<Atom>> SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Thorium Dioxide, Natural Source");
        setDescription("en_fan",
                "@E @[Discard 1 card from your hand]@: Look at the top 3 cards of your deck. Reveal 1 <<Atom>> SIGNI from among them, and add it to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "罗原 ThO2");
        setDescription("zh_simplified", 
                "@E 手牌1张舍弃:从你的牌组上面看3张牌。从中把<<原子>>精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(1), this::onEnterEff);
        }

        private void onEnterEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ATOM).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
