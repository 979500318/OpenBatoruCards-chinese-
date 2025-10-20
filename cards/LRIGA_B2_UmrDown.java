package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_B2_UmrDown extends Card {
    
    public LRIGA_B2_UmrDown()
    {
        setImageSets("WXDi-D01-010");
        
        setOriginalName("ウムル＝ダウン");
        setAltNames("ウムルダウン Umuru Daun");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをダウンする。\n" +
                "@E %B %X：対戦相手のシグニ１体を対象とし、それをダウンする。"
        );
        
        setName("en", "Umr =Down=");
        setDescription("en",
                "@E: Down target SIGNI on your opponent's field.\n" +
                "@E %B %X: Down target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Umr-Down");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and down it.\n" +
                "@E %B %X: Target 1 of your opponent's SIGNI, and down it."
        );
        
		setName("zh_simplified", "乌姆尔=下降");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其横置。\n" +
                "@E %B%X:对战对手的精灵1只作为对象，将其横置。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.UMR);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.BLUE);
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
            
            registerEnterAbility(this::onEnterEff);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
            down(cardIndex);
        }
    }
}
