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

public final class ARTS_B_CrossWater extends Card {

    public ARTS_B_CrossWater()
    {
        setImageSets("WX24-P4-031");

        setOriginalName("バツ・ウォーター");
        setAltNames("バツウォーター Batsu Uootaa");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のルリグかシグニ１体を対象とし、それをダウンする。\n" +
                "$$2対戦相手の赤か緑のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Cross Water");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's LRIG or SIGNI, and down it.\n" +
                "$$2 Target 1 of your opponent's red or green SIGNI, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "叉叉·水造");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的分身或精灵1只作为对象，将其#D。\n" +
                "$$2 对战对手的红色或绿色的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1));
        setUseTiming(UseTiming.ATTACK);

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
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().fromField()).get();
                down(target);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().withColor(CardColor.RED, CardColor.GREEN)).get();
                returnToDeck(target, DeckPosition.BOTTOM);
            }
        }
    }
}
