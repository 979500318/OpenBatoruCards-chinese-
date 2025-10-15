package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_R1_RilFlash extends Card {
    
    public LRIGA_R1_RilFlash()
    {
        setImageSets("WXDi-P08-027");
        
        setOriginalName("リル・一閃");
        setAltNames("リルイッセン Riru issen");
        setDescription("jp",
                "@E：対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Ril, Flash");
        setDescription("en",
                "@E: Vanish target level two or less SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Ril Flash");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 2 or lower SIGNI, and banish it."
        );
        
		setName("zh_simplified", "莉露·一闪");
        setDescription("zh_simplified", 
                "@E :对战对手的等级2以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.RIL);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
            banish(target);
        }
    }
}
