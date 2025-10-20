package open.batoru.data.cards;

import open.batoru.core.Deck;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_AyasCharmingInfatuation extends Card {

    public LRIGA_B2_AyasCharmingInfatuation()
    {
        setImageSets("WXDi-P09-035");

        setOriginalName("あーやの魅力にメロメロ！");
        setAltNames("アーヤノミリョクニメロメロ Aaya no Miryoku ni Meromero");
        setDescription("jp",
                "@E：対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "@E %X %X %X %X：対戦相手のダウン状態のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Aya's Enchantment!");
        setDescription("en",
                "@E: Down up to two target SIGNI on your opponent's field.\n" +
                "@E %X %X %X %X: Put target downed SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Aya's Charming Infatuation!");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "@E %X %X %X %X: Target 1 of your opponent's downed SIGNI, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "亚弥的魅惑！");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵2只最多作为对象，将这些横置。\n" +
                "@E %X %X %X %X:对战对手的横置状态的精灵1只作为对象，将其放置到牌组最下面。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AYA);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(4)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().downed()).get();
            returnToDeck(target, Deck.DeckPosition.BOTTOM);
        }
    }
}
