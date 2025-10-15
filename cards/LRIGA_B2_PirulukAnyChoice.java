package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_B2_PirulukAnyChoice extends Card {

    public LRIGA_B2_PirulukAnyChoice()
    {
        setImageSets("WX24-P2-045");

        setOriginalName("ピルルク/A-C");
        setAltNames("ピルルクエニーチョイス Piruruku Enii Choisu Piruluk/A-C Piruluk/AC");
        setDescription("jp",
                "@E：対戦相手のルリグ１体と対戦相手のシグニ１体を対象とし、それらをダウンする。\n" +
                "@E @[手札を３枚捨てる]@：対戦相手のルリグ１体と対戦相手のシグニ１体を対象とし、それらを凍結する。"
        );

        setName("en", "Piruluk/Any Choice");
        setDescription("en",
                "@E: Target 1 of your opponent's LRIG and 1 of your opponent's SIGNI, and down them.\n" +
                "@E @[Discard 3 cards from your hand]@: Target 1 of your opponent's LRIG and 1 of your opponent's SIGNI, and freeze them."
        );

		setName("zh_simplified", "皮璐璐可/A-C");
        setDescription("zh_simplified", 
                "@E 对战对手的分身1只和对战对手的精灵1只作为对象，将这些#D。\n" +
                "@E 手牌3张舍弃:对战对手的分身1只和对战对手的精灵1只作为对象，将这些冻结。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new DiscardCost(3), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            DataTable<CardIndex> targets = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().anyLRIG());
            targets.add(playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get());
            down(targets);
        }
        private void onEnterEff2()
        {
            DataTable<CardIndex> targets = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG());
            targets.add(playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get());
            freeze(targets);
        }
    }
}
