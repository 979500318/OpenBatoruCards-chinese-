package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_MuzicaSplit extends Card {
    
    public LRIGA_K2_MuzicaSplit()
    {
        setImageSets("WXDi-P02-033");
        
        setOriginalName("ムジカ／／スプリット");
        setAltNames("ムジカスプリット Mujika Supiritto");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。\n" +
                "@E %K %X %X %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );
        
        setName("en", "Muzica//Splits");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --10000 power until end of turn.\n" +
                "@E %K %X %X %X: Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Muzica//Splits");
        setDescription("en_fan",
                "@E: Target SIGNI on your opponent's field gets --10000 power until end of turn.\n" +
                "@E %K %X %X %X: Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Muzica//Split");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power.\n" +
                "@E %K %X %X %X: Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );
        
		setName("zh_simplified", "穆希卡//分裂");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。\n" +
                "@E %K%X %X %X:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MUZICA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLACK);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(3)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000, ChronoDuration.turnEnd());
        }
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
