package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_R2_RilInspiration extends Card {
    
    public LRIGA_R2_RilInspiration()
    {
        setImageSets("WXDi-P08-031");
        
        setOriginalName("リル・鼓舞");
        setAltNames("リルコブ Riru Kobu");
        setDescription("jp",
                "@E：カードを３枚引き、あなたの手札からシグニを１枚まで場に出す。その後、そのシグニよりパワーの低い対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Ril, Inspiration");
        setDescription("en",
                "@E: Draw three cards and put up to one SIGNI from your hand onto your field. Then, vanish target SIGNI on your opponent's field with power less than the power of that SIGNI."
        );
        
        setName("en_fan", "Ril Inspiration");
        setDescription("en_fan",
                "@E: Draw 3 cards, and put up to 1 SIGNI from your hand onto the field. Then, target 1 of your opponent's SIGNI with power less than that SIGNI, and banish it."
        );
        
		setName("zh_simplified", "莉露·鼓舞");
        setDescription("zh_simplified", 
                "@E :抽3张牌，从你的手牌把精灵1张最多出场。然后，比那只精灵的力量低的对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.RIL);
        setColor(CardColor.RED);
        setCost(Cost.colorless(1));
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
            draw(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromHand().playable()).get();
            
            if(cardIndex != null && putOnField(cardIndex))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, cardIndex.getIndexedInstance().getPower().getValue()-1)).get();
                banish(target);
            }
        }
    }
}
