package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_R2_DJLOVITBeatjug extends Card {
    
    public LRIGA_R2_DJLOVITBeatjug()
    {
        setImageSets("WXDi-P01-016");
        
        setOriginalName("DJ.LOVIT-BEATJUG");
        setAltNames("ディージェーラビットビートジャグ Diijee Rabitto Biitojagu");
        setDescription("jp",
                "@E：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %R %X %X %X %X：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "DJ LOVIT - BEATJUG");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 10000 or less.\n" +
                "@E %R %X %X %X %X: Vanish target SIGNI on your opponent's field with power 10000 or less."
        );
        
        setName("en_fan", "DJ.LOVIT - BEATJUG");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 10000 or less, and banish it.\n" +
                "@E %R %X %X %X %X: Target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );
        
		setName("zh_simplified", "DJ.LOVIT-BEATJUG");
        setDescription("zh_simplified", 
                "@E :对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n" +
                "@E %R%X %X %X %X:对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LOVIT);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.RED);
        setCost(Cost.colorless(1));
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
            
            registerEnterAbility(this::onEnterEff);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(4)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }
    }
}
