package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_MuzicaStomping extends Card {
    
    public LRIGA_K2_MuzicaStomping()
    {
        setImageSets("WXDi-P03-033");
        
        setOriginalName("ムジカ／／ストンピング");
        setAltNames("ムジカストンピング Mujika Sutonpingu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %K %K %K %K %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－12000する。"
        );
        
        setName("en", "Muzica//Stomping");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field.\n" +
                "@E %K %K %K %K %X: Target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Muzica//Stomping");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@E %K %K %K %K %X: Target 1 of your opponent's SIGNI, and until end of turn, it gets --12000 power."
        );
        
		setName("zh_simplified", "穆希卡//重踏");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@E %K %K %K %K%X:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-12000。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MUZICA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLACK);
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 4) + Cost.colorless(1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -12000, ChronoDuration.turnEnd());
        }
    }
}
