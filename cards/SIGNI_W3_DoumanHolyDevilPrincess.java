package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_W3_DoumanHolyDevilPrincess extends Card {
    
    public SIGNI_W3_DoumanHolyDevilPrincess()
    {
        setImageSets("WXDi-P04-033");
        
        setOriginalName("聖魔姫　ドーマン");
        setAltNames("セイマキドーマン Seimaki Dooman");
        setDescription("jp",
                "@U：あなたのメインフェイズ開始時、あなたのトラッシュから#Gを持つシグニ１枚を対象とし、このシグニを場からトラッシュに置いてもよい。そうした場合、それを手札に加える。\n" +
                "@E @[手札から#Gを持つシグニを１枚捨てる]@：対戦相手のレベル２以下のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "Douman, Blessed Evil Queen");
        setDescription("en",
                "@U: At the beginning of your main phase, you may put this SIGNI on your field into its owner's trash. If you do, add target SIGNI with a #G from your trash to your hand.\n" +
                "@E @[Discard a SIGNI with a #G]@: Return target level two or less SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Douman, Holy Devil Princess");
        setDescription("en_fan",
                "@U: At the beginning of your main phase, target 1 #G @[Guard]@ SIGNI from your trash, and you may put this SIGNI from the field into the trash. If you do, add it to your hand.\n" +
                "@E @[Discard 1 #G @[Guard]@ SIGNI from your hand]@: Target 1 of your opponent's level 2 or lower SIGNI, and return it to their hand."
        );
        
		setName("zh_simplified", "圣魔姬 道满");
        setDescription("zh_simplified", 
                "@U 你的主要阶段开始时，从你的废弃区把持有#G的精灵1张作为对象，可以把这只精灵从场上放置到废弃区。这样做的场合，将其加入手牌。\n" +
                "@E 从手牌把持有#G的精灵1张舍弃:对战对手的等级2以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new DiscardCost(new TargetFilter().SIGNI().withState(CardStateFlag.CAN_GUARD)), this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            
            if(target != null && getCardIndex().isSIGNIOnField() && playerChoiceActivate() && trash(getCardIndex()))
            {
                addToHand(target);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(0,2)).get();
            addToHand(target);
        }
    }
}
