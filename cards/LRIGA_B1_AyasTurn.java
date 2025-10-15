package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B1_AyasTurn extends Card {

    public LRIGA_B1_AyasTurn()
    {
        setImageSets("WXDi-P09-032");

        setOriginalName("あーやの出番！");
        setAltNames("アーヤノデバン Aaya no Deban");
        setDescription("jp",
                "@E：対戦相手のパワー5000以下のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Aya's Turn!");
        setDescription("en",
                "@E: Put target SIGNI on your opponent's field with power 5000 or less on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Aya's Turn!");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 5000 or less, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "亚弥的入场！");
        setDescription("zh_simplified", 
                "@E :对战对手的力量5000以下的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AYA);
        setColor(CardColor.BLUE);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().withPower(0,5000)).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
