package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class LRIGA_G2_MelPresent extends Card {
    
    public LRIGA_G2_MelPresent()
    {
        setImageSets("WXDi-P07-032");
        
        setOriginalName("メル・プレゼント");
        setAltNames("メルプレゼント Meru Purezento");
        setDescription("jp",
                "@E：あなたのエナゾーンからシグニ１枚を対象とし、それを場に出す。それの@E能力は発動しない。\n" +
                "@A $G1 %G %X：あなたのシグニ１体を対象とし、ターン終了時まで、それは【ランサー】を得る。"
        );
        
        setName("en", "Mel Present");
        setDescription("en",
                "@E: Put target SIGNI from your Ener Zone onto your field. The @E abilities of SIGNI put onto your field this way do not activate.\n" +
                "@A $G1 %G %X: Target SIGNI on your field gains [[Lancer]] until end of turn."
        );
        
        setName("en_fan", "Mel Present");
        setDescription("en_fan",
                "@E: Target 1 SIGNI from your ener zone, and put it onto the field. Its @E abilities don't activate.\n" +
                "@A $G1 %G %X: Target 1 of your SIGNI, and until end of turn, it gains [[Lancer]]."
        );
        
		setName("zh_simplified", "梅露·展示");
        setDescription("zh_simplified", 
                "@E 从你的能量区把精灵1张作为对象，将其出场。其的@E能力不能发动。\n" +
                "@A $G1 %G%X:你的精灵1只作为对象，直到回合结束时为止，其得到[[枪兵]]。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MEL);
        setColor(CardColor.GREEN);
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
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromEner().playable()).get();
            putOnField(target, Enter.DONT_ACTIVATE);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityLancer(), ChronoDuration.turnEnd());
        }
    }
}
