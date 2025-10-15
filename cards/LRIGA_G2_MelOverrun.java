package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_MelOverrun extends Card {
    
    public LRIGA_G2_MelOverrun()
    {
        setImageSets("WXDi-P07-031");
        
        setOriginalName("メル・オーバーラン");
        setAltNames("メルオーバーラン Meru Oobaaran");
        setDescription("jp",
                "@E：対戦相手のパワー10000以上のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %G %X %X %X %X：あなたのいずれかのシグニよりパワーの低い対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Mel Overrun");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 10000 or more.\n" +
                "@E %G %X %X %X %X: Vanish target SIGNI on your opponent's field with power less than a SIGNI on your field."
        );
        
        setName("en_fan", "Mel Overrun");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 10000 or more, and banish it.\n" +
                "@E %G %X %X %X %X: Target 1 of your opponent's SIGNI with power less than any of your SIGNI, and banish it."
        );
        
		setName("zh_simplified", "梅露·覆盖");
        setDescription("zh_simplified", 
                "@E :对战对手的力量10000以上的精灵1只作为对象，将其破坏。\n" +
                "@E %G%X %X %X %X:比你的任一只的精灵的力量低的对战对手的精灵1只作为对象，将其破坏。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MEL);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(1));
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(4)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            double max = getSIGNIOnField(getOwner()).stream().mapToDouble(c -> ((CardIndex)c).getIndexedInstance().getPower().getValue()).max().orElse(0);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, max-1)).get();
            banish(target);
        }
    }
}
