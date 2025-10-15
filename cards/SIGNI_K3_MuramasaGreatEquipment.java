package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_K3_MuramasaGreatEquipment extends Card {
    
    public SIGNI_K3_MuramasaGreatEquipment()
    {
        setImageSets("WXDi-P04-041");
        
        setOriginalName("大装　ムラマサ");
        setAltNames("タイソウムラマサ Taisou Muramasa");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのトラッシュからシグニを１枚を対象とし、あなたの他のシグニ１体を場からトラッシュに置き%Kを支払ってもよい。そうした場合、それを手札に加える。\n" +
                "@U：対戦相手のアタックフェイズ開始時、あなたのトラッシュから黒のシグニ１枚を対象とし、%Kを支払ってもよい。そうした場合、それを手札に加える。" +
                "~#：あなたのトラッシュからシグニを２枚まで対象とし、それらを手札に加える。手札を１枚捨てる。"
        );
        
        setName("en", "Muramasa, Full Armed");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may put another SIGNI on your field into its owner's trash and pay %K. If you do, add target SIGNI from your trash to your hand.\n" +
                "@U: At the beginning of your opponent's attack phase, you may pay %K. If you do, add target black SIGNI from your trash to your hand." +
                "~#Add up to two target SIGNI from your trash to your hand. Discard a card."
        );
        
        setName("en_fan", "Muramasa, Great Equipment");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, target 1 SIGNI from your trash, and you may put 1 of your other SIGNI from the field into the trash and pay %K. If you do, add it to your hand.\n" +
                "@U: At the beginning of your opponent's attack phase, target 1 black SIGNI from your trash, and you may pay %K. If you do, add it to your hand." +
                "~#Target up to 2 SIGNI from your trash, and add them to your hand. Discard 1 card from your hand."
        );
        
		setName("zh_simplified", "大装 村正");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从你的废弃区把精灵1张作为对象，可以把你的其他的精灵1只从场上放置到废弃区并支付%K。这样做的场合，将其加入手牌。\n" +
                "@U :对战对手的攻击阶段开始时，从你的废弃区把黑色的精灵1张作为对象，可以支付%K。这样做的场合，将其加入手牌。" +
                "~#从你的废弃区把精灵2张最多作为对象，将这些加入手牌。手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto.setCondition(this::onAutoEff2Cond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().except(getCardIndex())).get();
                
                if(trash(cardIndex) && payEner(Cost.color(CardColor.BLACK, 1)))
                {
                    addToHand(target);
                }
            }
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash()).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
            {
                addToHand(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash());
            addToHand(data);
            
            discard(1);
        }
    }
}
