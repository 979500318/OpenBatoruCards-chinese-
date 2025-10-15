package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_G1_VJWOLFCue extends Card {
    
    public LRIGA_G1_VJWOLFCue()
    {
        setImageSets("WXDi-D04-009");
        
        setOriginalName("VJ.WOLF-CUE");
        setAltNames("ブイジェーウルフキュー Buijee Urufu Kyuu");
        setDescription("jp",
                "@E：対戦相手のパワー7000以上のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "VJ WOLF - CUE");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 7000 or more."
        );
        
        setName("en_fan", "VJ.WOLF - CUE");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );
        
		setName("zh_simplified", "VJ.WOLF-CUE");
        setDescription("zh_simplified", 
                "@E :对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.WOLF);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.GREEN);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}
