package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_EldoraStone extends Card {

    public LRIGA_B2_EldoraStone()
    {
        setImageSets("WXDi-P12-037");

        setOriginalName("エルドラ！ストーン！");
        setAltNames("エルドラストーン Erudora Sutoon");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをデッキの一番下に置く。\n" +
                "@E %B %X：カードを２枚引く。"
        );

        setName("en", "Eldora! Stone!");
        setDescription("en",
                "@E: Put target SIGNI on your opponent's field on the bottom of its owner's deck.\n@E %B %X: Draw two cards."
        );
        
        setName("en_fan", "Eldora! Stone!");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and put it to the bottom of their deck.\n" +
                "@E %B %X: Draw 2 cards."
        );

		setName("zh_simplified", "艾尔德拉！敲击！");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其放置到牌组最下面。\n" +
                "@E %B%X:抽2张牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ELDORA);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }

        private void onEnterEff2()
        {
            draw(2);
        }
    }
}

