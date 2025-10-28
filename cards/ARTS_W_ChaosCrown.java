package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class ARTS_W_ChaosCrown extends Card {

    public ARTS_W_ChaosCrown()
    {
        setImageSets("WX24-P2-031");

        setOriginalName("カオス・クラウン");
        setAltNames("カオスクラウン Kaosu Kuraun");
        setDescription("jp",
                "対戦相手のパワー10000以下のシグニ１体を対象とし、それを手札に戻す。あなたのデッキの上からカードを７枚見る。その中から＜天使＞と＜悪魔＞のシグニをそれぞれ１枚まで公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。"
        );

        setName("en", "Chaos Crown");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 10000 or less, and return it to their hand. Look at the top 7 cards of your deck, reveal up to 1 of each <<Angel>> and <<Devil>> SIGNI from among them, add them to your hand, and shuffle the rest and put them on the bottom of your deck."
        );

        setName("es", "Corona del caos");
        setDescription("es",
                "Selecciona 1 SIGNI oponente con 10000 o menos poder y devuelvela a la mano. Mira 7 cartas del tope de tu mazo, revela hasta 1 SIGNI <<Ángel>> y 1 SIGNI <<Demonio>> de entre ellas y añadelas a tu mano, baraja el resto y ponlas en el fondo del mazo."
        );

        setName("zh_simplified", "混沌·皇冠");
        setDescription("zh_simplified", 
                "对战对手的力量10000以下的精灵1只作为对象，将其返回手牌。从你的牌组上面看7张牌。从中把<<天使>>和<<悪魔>>精灵各1张最多公开加入手牌，剩下的洗切放置到牌组最下面。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,10000)).get();
            addToHand(target);
            
            look(7);
            
            DataTable<CardIndex> data = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromLooked());
            data.add(playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.DEVIL).except(data.get()).fromLooked()).get());
            
            reveal(data);
            addToHand(data);
            
            int countReturned = returnToDeck(getCardsInLooked(getOwner()), DeckPosition.BOTTOM);
            shuffleDeck(countReturned, DeckPosition.BOTTOM);
        }
    }
}
