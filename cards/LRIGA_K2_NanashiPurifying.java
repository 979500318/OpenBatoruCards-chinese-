package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_NanashiPurifying extends Card {
    
    public LRIGA_K2_NanashiPurifying()
    {
        setImageSets("WXDi-P07-036");
        
        setOriginalName("ナナシ・浄化");
        setAltNames("ナナシジョウカ Nanashi Jouka");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。それが感染状態の場合、代わりにそれをトラッシュに置く。\n" +
                "@E %X %X：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Nanashi Purification");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field. If it is infected, instead put it into its owner's trash.\n\n" +
                "@E %X %X: Add target SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "Nanashi Purifying");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it. If it is infected, instead put it into the trash.\n" +
                "@E %X %X: Target 1 SIGNI without #G @[Guard]@ from you trash, and add it to your hand."
        );
        
		setName("zh_simplified", "无名·净化");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。其是感染状态的场合，作为替代，将其放置到废弃区。\n" +
                "@E %X %X从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.NANASHI);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(2));
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI()).get();
            
            if(target != null)
            {
                if(!target.getIndexedInstance().hasZoneObject(CardUnderType.ZONE_VIRUS))
                {
                    banish(target);
                } else {
                    trash(target);
                }
            }
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            addToHand(target);
        }
    }
}
