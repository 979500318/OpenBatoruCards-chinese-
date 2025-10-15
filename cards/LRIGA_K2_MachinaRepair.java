package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K2_MachinaRepair extends Card {
    
    public LRIGA_K2_MachinaRepair()
    {
        setImageSets("WXDi-P04-026");
        
        setOriginalName("マキナリペア");
        setAltNames("Makina Ripea");
        setDescription("jp",
                "@E：あなたのトラッシュから##を持たないカード１枚を対象とし、それをライフクロスに加える。"
        );
        
        setName("en", "Machina Repair");
        setDescription("en",
                "@E: Add target card without ## from your trash to your Life Cloth."
        );
        
        setName("en_fan", "Machina Repair");
        setDescription("en_fan",
                "@E: Target 1 card without ## @[Life Burst]@ from your trash, and add it to life cloth."
        );
        
		setName("zh_simplified", "玛琪娜修补");
        setDescription("zh_simplified", 
                "@E 从你的废弃区把不持有##的牌1张作为对象，将其加入生命护甲。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MACHINA);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HEAL).own().not(new TargetFilter().lifeBurst()).fromTrash()).get();
            addToLifeCloth(target);
        }
    }
}
