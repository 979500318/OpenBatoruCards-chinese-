package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_R2_DJLOVITCrossfade extends Card {
    
    public LRIGA_R2_DJLOVITCrossfade()
    {
        setImageSets("WXDi-P01-017");
        
        setOriginalName("DJ.LOVIT-CROSSFADE");
        setAltNames("ディージェーラビットクロスフェイド Diijee Rabitto Kurosufeido");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %R %X %X %X：対戦相手のライフクロス１枚をクラッシュする。"
        );
        
        setName("en", "DJ LOVIT - CROSSFADE");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field.\n" +
                "@E %R %X %X %X: Crush one of your opponent's Life Cloth."
        );
        
        setName("en_fan", "DJ.LOVIT - CROSSFADE");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@E %R %X %X %X: Crush 1 of your opponent's life cloth."
        );
        
		setName("zh_simplified", "DJ.LOVIT-CROSSFADE");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@E %R%X %X %X:对战对手的生命护甲1张击溃。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LOVIT);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(3)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
        private void onEnterEff2()
        {
            crush(getOpponent());
        }
    }
}
