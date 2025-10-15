package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_R2_HiranaStraight extends Card {
    
    public LRIGA_R2_HiranaStraight()
    {
        setImageSets("WXDi-P02-012");
        
        setOriginalName("ヒラナ＊ストレート");
        setAltNames("ヒラナストレート Hirana Sutoreeto");
        setDescription("jp",
                "@E：対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %X %X %X %X %X：対戦相手のレベル３のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Hirana*Honest and Earnest");
        setDescription("en",
                "@E: Vanish target level two or less SIGNI on your opponent's field.\n" +
                "@E %X %X %X %X %X: Vanish target level three SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Hirana*Straight");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 2 or lower SIGNI, and banish it.\n" +
                "@E %X %X %X %X %X: Target 1 of your opponent's level 3 SIGNI, and banish it."
        );
        
		setName("zh_simplified", "平和＊上升中");
        setDescription("zh_simplified", 
                "@E :对战对手的等级2以下的精灵1只作为对象，将其破坏。\n" +
                "@E %X %X %X %X %X:对战对手的等级3的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.HIRANA);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.RED);
        setCost(Cost.colorless(2));
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
            registerEnterAbility(new EnerCost(Cost.colorless(5)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(3)).get();
            banish(target);
        }
    }
}
