package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K1_MachinaWingslash extends Card {
    
    public LRIGA_K1_MachinaWingslash()
    {
        setImageSets("WXDi-D07-009");
        
        setOriginalName("マキナウィングスラッシュ");
        setAltNames("Makina Uuingusurasshu");
        setDescription("jp",
                "@E：対戦相手のレベル１のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E：あなたのトラッシュから#Gを持たないレベル１のシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Machina Wing Slash");
        setDescription("en",
                "@E: Vanish target level one SIGNI on your opponent's field.\n" +
                "@E: Add target level one SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "Machina Wingslash");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 1 SIGNI, and banish it.\n" +
                "@E: Target 1 level 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "玛琪娜翼斩");
        setDescription("zh_simplified", 
                "@E :对战对手的等级1的精灵1只作为对象，将其破坏。\n" +
                "@E 从你的废弃区把不持有#G的等级1的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MACHINA);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(1)).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withLevel(1).not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            addToHand(target);
        }
    }
}
