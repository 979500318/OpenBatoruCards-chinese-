package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class ARTS_W_FortuneEnvision extends Card {

    public ARTS_W_FortuneEnvision()
    {
        setImageSets("WX24-P3-031");

        setOriginalName("フォーチュン・エンヴィジョン");
        setAltNames("フォーチュンエンヴィジョン Foochun Enbuijon");
        setDescription("jp",
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それを手札に戻す。あなたのデッキの上からカードを５枚見る。その中から＜宇宙＞のシグニを２枚まで公開し手札に加え、好きな枚数のカードを好きな順番でデッキの一番下に置き、残りを好きな順番でデッキの一番上に戻す。"
        );

        setName("en", "Fortune Envision");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 8000 or less, and return it to their hand. Look at the top 5 cards of your deck. Reveal up to 2 <<Space>> SIGNI from among them, and add them to your hand, put any number of cards from among them on the bottom of your deck, and put the rest on the top of your deck in any order."
        );

        setName("es", "Imaginación fortunosa");
        setDescription("es",
                "Selecciona 1 SIGNI oponente con 8000 o menos poder y devuelvela a la mano. Mira 5 cartas del tope de tu mazo y revela hasta 2 SIGNI <<Space>> de entre ellas, añadela a tu mano y pon cualquier número de entre ellas en el fondo de tu mazo, pon el resto en el tope de tu mazo en cualquier orden."
        );

        setName("zh_simplified", "强运·设想");
        setDescription("zh_simplified", 
                "对战对手的力量8000以下的精灵1只作为对象，将其返回手牌。从你的牌组上面看5张牌。从中把<<宇宙>>精灵2张最多公开加入手牌，任意张数的牌任意顺序放置到牌组最下面，剩下的任意顺序返回牌组最上面。"
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,8000)).get();
            addToHand(target);
            
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.SPACE).fromLooked());
            reveal(data);
            addToHand(data);
            
            data = playerTargetCard(0,getLookedCount(), new TargetFilter(TargetHint.BOTTOM).own().fromLooked());
            returnToDeck(data, DeckPosition.BOTTOM);
            
            returnToDeckOrdered(CardLocation.LOOKED, DeckPosition.TOP);
        }
    }
}
