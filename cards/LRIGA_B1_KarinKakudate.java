package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_B1_KarinKakudate extends Card {

    public LRIGA_B1_KarinKakudate()
    {
        setImageSets("WXDi-CP02-032");

        setOriginalName("角楯カリン");
        setAltNames("カクダテカリン Kakudate Karin");
        setDescription("jp",
                "@E：対戦相手のレベル１のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Kakudate Karin");
        setDescription("en",
                "@E: Put target level one SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Karin Kakudate");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 1 SIGNI, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "角楯花凛");
        setDescription("zh_simplified", 
                "@E :对战对手的等级1的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.CC);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().withLevel(1)).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}

