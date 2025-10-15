package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_R2_RilDrawnSword extends Card {
    
    public LRIGA_R2_RilDrawnSword()
    {
        setImageSets("WXDi-P08-029");
        
        setOriginalName("リル・抜刀");
        setAltNames("リルバットウ Riru Battou");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A $G1 %R %X %X：対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Ril, Drawn Sword");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field.\n" +
                "@A $G1 %R %X %X: Vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Ril Drawn Sword");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@A $G1 %R %X %X: Target 1 of your opponent's SIGNI, and banish it."
        );
        
		setName("zh_simplified", "莉露·拔刀");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@A $G1 %R%X %X:对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.RIL);
        setColor(CardColor.RED);
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
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(2)), this::onEnterEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
    }
}
