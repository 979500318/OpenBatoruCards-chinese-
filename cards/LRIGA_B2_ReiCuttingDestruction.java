package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_ReiCuttingDestruction extends Card {
    
    public LRIGA_B2_ReiCuttingDestruction()
    {
        setImageSets("WXDi-P01-014");
        
        setOriginalName("レイ＊斬破");
        setAltNames("レイザンパ Rei Zanpa");
        setDescription("jp",
                "@E：対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "@E %B %X %X：対戦相手のシグニ１体を対象とし、それをダウンする。"
        );
        
        setName("en", "Rei*Rending Blade");
        setDescription("en",
                "@E: Down up to two target SIGNI on your opponent's field.\n" +
                "@E %B %X %X: Down target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Rei*Cutting Destruction");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "@E %B %X %X: Target 1 of your opponent's SIGNI, and down it."
        );
        
		setName("zh_simplified", "令＊斩破");
        setDescription("zh_simplified", 
                "@E 对战对手的精灵2只最多作为对象，将这些#D。\n" +
                "@E %B%X %X对战对手的精灵1只作为对象，将其#D。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.REI);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(4));
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(2)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
            down(target);
        }
    }
}
