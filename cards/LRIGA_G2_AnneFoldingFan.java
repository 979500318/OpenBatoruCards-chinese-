package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_AnneFoldingFan extends Card {

    public LRIGA_G2_AnneFoldingFan()
    {
        setImageSets("WX24-P3-045");

        setOriginalName("アン － 扇ギ");
        setAltNames("アンアオギ An Aogi");
        setDescription("jp",
                "@E：対戦相手のシグニを２体まで対象とし、このターン、それらがそれぞれ次にアタックしたとき、そのアタックを無効にする。\n" +
                "@E %X %X %X %X：対戦相手のシグニ１体を対象とし、このターン、次にそれがアタックしたとき、そのアタックを無効にする。"
        );

        setName("en", "Anne Folding Fan");
        setDescription("en",
                "@E: Target up to 2 of your opponent's SIGNI, and this turn, the next time each of them attacks, disable that attack.\n" +
                "@E %X %X %X %X: Target 1 of your opponent's SIGNI, and this turn, the next time it attacks, disable that attack."
        );

		setName("zh_simplified", "安 - 扇御");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵2只最多作为对象，这个回合，当这些下一次攻击时，那次攻击无效。\n" +
                "@E %X %X %X %X:对战对手的精灵1只作为对象，这个回合，当下一次其攻击时，那次攻击无效。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
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
            registerEnterAbility(new EnerCost(Cost.colorless(4)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().SIGNI());
            if(data.get() != null) for(int i=0;i<data.size();i++) disableNextAttack(data.get(i));
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) disableNextAttack(target);
        }
    }
}
