package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.TrashCost;

import java.util.List;

public final class LRIGA_G2_AtFinder extends Card {
    
    public LRIGA_G2_AtFinder()
    {
        setImageSets("WXDi-P00-022");
        
        setOriginalName("アト＝ファインダー");
        setAltNames("アトファインダー Ato Faindaa");
        setDescription("jp",
                "@E：このターン、次にあなたがダメージを受ける場合、代わりにダメージを受けない。\n" +
                "@E @[エナゾーンからそれぞれ異なるクラスを持つシグニ３枚をトラッシュに置く]@：対戦相手のパワー10000以上のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "At =Discovery=");
        setDescription("en",
                "@E: The next time you would take damage this turn, instead you do not take that damage.\n" +
                "@E @[Put three SIGNI with different classes from your Ener Zone into their owner's trash]@: Vanish target SIGNI on your opponent's field with power 10000 or more."
        );
        
        setName("en_fan", "At-Finder");
        setDescription("en_fan",
                "@E: This turn, the next time you would be damaged, instead you aren't damaged.\n" +
                "@E @[Put 3 SIGNI with different classes from your ener zone into the trash]@: Target 1 of your opponent's SIGNI with power 10000 or more, and banish it."
        );
        
		setName("zh_simplified", "亚特=发现");
        setDescription("zh_simplified", 
                "@E :这个回合，下一次你受到伤害的场合，作为替代，不会受到伤害。\n" +
                "@E 从能量区把持有不同类别的精灵3张放置到废弃区:对战对手的力量10000以上的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AT);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(2));
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
            
            EnterAbility op2 = registerEnterAbility(new TrashCost(3, new TargetFilter().SIGNI().fromEner(), this::onEnterEff2CostTargetCond), this::onEnterEff2);
            op2.setCondition(this::onEnterEff2Cond);
        }
        
        private void onEnterEff1()
        {
            blockNextDamage();
        }
        
        private boolean onEnterEff2CostTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 3 && CardAbilities.getSIGNIClasses(new DataTable<>(listPickedCards)).size() >= 3;
        }
        private ConditionState onEnterEff2Cond()
        {
            return CardAbilities.getSIGNIClasses(getCardsInEner(getOwner())).size() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            banish(target);
        }
    }
}
