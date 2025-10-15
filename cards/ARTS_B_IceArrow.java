package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.CardDataImageSet.Mask;

public final class ARTS_B_IceArrow extends Card {

    public ARTS_B_IceArrow()
    {
        setImageSets("WX24-P1-024", Mask.IGNORE+"SPDi07-13");

        setOriginalName("アイス・アロー");
        setAltNames("アイスアロー Aisu Aroo");
        setDescription("jp",
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Ice Arrow");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 8000 or less, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "寒冰·矢箭");
        setDescription("zh_simplified", 
                "对战对手的力量8000以下的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}

