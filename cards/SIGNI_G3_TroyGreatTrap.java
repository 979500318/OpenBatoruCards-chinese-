package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_G3_TroyGreatTrap extends Card {

    public SIGNI_G3_TroyGreatTrap()
    {
        setImageSets("WX25-P1-059");

        setOriginalName("大罠　トロイ");
        setAltNames("ダイビントロイ Daibin Toroi");
        setDescription("jp",
                "@A $T1 %G0：対戦相手のシグニ１体と、対戦相手のエナゾーンからそれと同じレベルのシグニ１枚を対象とし、それらの場所を入れ替える。この方法で場に出たシグニの@E能力は発動しない。\n" +
                "@A %G %X：ターン終了時まで、このシグニは[[アサシン（パワー12000以上のシグニ）]]を得る。"
        );

        setName("en", "Troy, Great Trap");
        setDescription("en",
                "@A $T1 %G0: Target 1 of your opponent's SIGNI and 1 SIGNI with the same level as that SIGNI from your opponent's ener zone, and exchange their positions. The @E abilities of the SIGNI put onto the field this way don't activate.\n" +
                "@A %G %X: Until end of turn, this SIGNI gains [[Assassin (SIGNI with power 12000 or more)]]."
        );

		setName("zh_simplified", "大罠 特洛伊");
        setDescription("zh_simplified", 
                "@A $T1 %G0对战对手的精灵1只和，从对战对手的能量区把与其相同等级的精灵1张作为对象，将这些的场所交换。这个方法出场的精灵的@E能力不能发动。\n" +
                "@A %G%X:直到回合结束时为止，这只精灵得到[[暗杀（力量12000以上的精灵）]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)), this::onActionEff2);
            act2.setCondition(this::onActionEff2Cond);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MOVE).OP().SIGNI()).get();
            
            if(target != null)
            {
                CardIndex targetInEner = playerTargetCard(new TargetFilter(TargetHint.MOVE).OP().SIGNI().fromEner().withLevel(target.getIndexedInstance().getLevel().getValue()).playableAs(target)).get();
                
                if(targetInEner != null)
                {
                    putInEner(target);
                    putOnField(targetInEner, target.getPreTransientLocation(), Enter.DONT_ACTIVATE);
                }
            }
        }
        
        private ConditionState onActionEff2Cond()
        {
            return getCardIndex().getIndexedInstance().getAbilityList().stream().anyMatch(ability ->
                    ability.getSourceStockAbility() instanceof StockAbilityAssassin) ? ConditionState.WARN : ConditionState.OK;
        }
        private void onActionEff2()
        {
            attachAbility(getCardIndex(), new StockAbilityAssassin(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexOpposite)
        {
            return cardIndexOpposite.getIndexedInstance().getPower().getValue() >= 12000 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
