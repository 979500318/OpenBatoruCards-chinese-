package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;

public final class ARTS_B_CoolSlap extends Card {

    public ARTS_B_CoolSlap()
    {
        setImageSets("WX24-D3-05");

        setOriginalName("クール・スラップ");
        setAltNames("クールスラップ Kuuru Surappu");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、それをデッキの一番下に置く。対戦相手の手札を１枚見ないで選び、捨てさせる。"
        );

        setName("en", "Cool Slap");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and put it on the bottom of their deck. Choose 1 card from your opponent's hand without looking, and discard it."
        );

        setName("es", "Cool Slap");
        setDescription("es",
                "Selecciona 1 SIGNI oponente y ponla en el fondo del mazo. Elige 1 carta de la mano oponente sin ver y descartala."
        );

        setName("zh_simplified", "冷酷·掌掴");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，将其放置到牌组最下面。不看对战对手的手牌选1张，舍弃。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
            
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
    }
}

