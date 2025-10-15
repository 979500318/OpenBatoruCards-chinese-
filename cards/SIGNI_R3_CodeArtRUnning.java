package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R3_CodeArtRUnning extends Card {
    
    public SIGNI_R3_CodeArtRUnning()
    {
        setImageSets("WXDi-P05-059");
        
        setOriginalName("コードアート　Ｒンニング");
        setAltNames("コードアートアールンニング Koodo Aato Aaru Ningu Running");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のエナゾーンにカードが２枚以上ある場合、手札から赤のスペルを１枚捨ててもよい。そうした場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n" +
                "@U：このシグニがアタックしたとき、あなたのトラッシュから赤のスペル１枚を対象とし、%Rを支払ってもよい。そうした場合、それを手札に加える。"
        );
        
        setName("en", "R - Unning, Code: Art");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are two or more cards in your opponent's Ener Zone, you may discard a red spell. If you do, your opponent chooses a card from their Ener Zone and puts it into their trash.\n" +
                "@U: Whenever this SIGNI attacks, you may pay %R. If you do, add target red spell from your trash to your hand."
        );
        
        setName("en_fan", "Code Art, R Unning");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 2 or more cards in your opponent's ener zone, you may discard 1 red spell from your hand. If you do, your opponent chooses 1 card from their ener zone, and puts it into the trash.\n" +
                "@U: Whenever this SIGNI attacks, target 1 spell from your trash, and you may pay %R. If you do, add it to your hand."
        );
        
		setName("zh_simplified", "必杀代号 跑步机");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的能量区的牌在2张以上的场合，可以从手牌把红色的魔法1张舍弃。这样做的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "@U :当这只精灵攻击时，从你的废弃区把红色的魔法1张作为对象，可以支付%R。这样做的场合，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getEnerCount(getOpponent()) >= 2 && discard(0,1, new TargetFilter().spell().withColor(CardColor.RED)).get() != null)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
        }
        
        private void onAutoEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().spell().withColor(CardColor.RED).fromTrash()).get();
            
            if(target != null && payEner(Cost.color(CardColor.RED, 1)))
            {
                addToHand(target);
            }
        }
    }
}
