package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_R1_DJLOVITScratch extends Card {
    
    public LRIGA_R1_DJLOVITScratch()
    {
        setImageSets("WXDi-D04-006");
        
        setOriginalName("DJ.LOVIT-SCRATCH");
        setAltNames("ディージェーラビットスクラッチ Diijee Rabitto Sukuratchi");
        setDescription("jp",
                "@E：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "DJ LOVIT - SCRATCH");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 10000 or less."
        );
        
        setName("en_fan", "DJ.LOVIT - SCRATCH");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );
        
		setName("zh_simplified", "DJ.LOVIT-SCRATCH");
        setDescription("zh_simplified", 
                "@E :对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LOVIT);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.RED);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
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
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }
    }
}
