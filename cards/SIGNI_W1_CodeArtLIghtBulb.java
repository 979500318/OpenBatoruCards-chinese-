package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_W1_CodeArtLIghtBulb extends Card {

    public SIGNI_W1_CodeArtLIghtBulb()
    {
        setImageSets("WX25-P2-066");

        setOriginalName("コードアート　Hクネツ");
        setAltNames("コードアートエイチクネツ Koodo Aato Eichi Kunetsu Lightbulb Light Bulb");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを７枚見る。その中からスペルを１枚まで公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。この方法でカードを手札に加えた場合、手札を１枚捨てる。"
        );

        setName("en", "Code Art L Ight Bulb");
        setDescription("en",
                "@E: Look at the top 7 cards of your deck. Reveal up to 1 spell from among them, add it to your hand, and shuffle the rest and put them on the bottom of your deck. If you added a card to your hand this way, discard 1 card from your hand."
        );

		setName("zh_simplified", "必杀代号 白炽灯");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看7张牌。从中把魔法1张最多公开加入手牌，剩下的洗切放置到牌组最下面。这个方法把牌加入手牌的场合，手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            look(7);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().spell().fromLooked()).get();
            reveal(cardIndex);
            boolean added = addToHand(cardIndex);
            
            int countReturned = returnToDeck(getCardsInLooked(getOwner()), DeckPosition.BOTTOM);
            shuffleDeck(countReturned, DeckPosition.BOTTOM);
            
            if(added)
            {
                discard(1);
            }
        }
    }
}
