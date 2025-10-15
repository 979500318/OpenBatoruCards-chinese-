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

public final class SIGNI_W2_WordCardHolyWisdom extends Card {
    
    public SIGNI_W2_WordCardHolyWisdom()
    {
        setImageSets("WXDi-P08-052");
        
        setOriginalName("聖英　タンゴカード");
        setAltNames("セイエイタンゴカード Seiei Tango Kaado");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に白のシグニが３体以上ある場合、対戦相手のパワー3000以下のシグニ１体を対象とし、%Wを支払ってもよい。そうした場合、それを手札に戻す。"
        );
        
        setName("en", "Flash Cards, Blessed Mind");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are three or more white SIGNI on your field, you may pay %W. If you do, return target SIGNI on your opponent's field with power 3000 or less to its owner's hand."
        );
        
        setName("en_fan", "Word Card, Holy Wisdom");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 3 or more white SIGNI on your field, target 1 of your opponent's SIGNI with power 3000 or less, and you may pay %W. If you do, return it to their hand."
        );
        
		setName("zh_simplified", "圣英 单词卡");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的白色的精灵在3只以上的场合，对战对手的力量3000以下的精灵1只作为对象，可以支付%W。这样做的场合，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
        setLevel(2);
        setPower(8000);
        
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
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withColor(CardColor.WHITE).getValidTargetsCount() >= 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,3000)).get();
                
                if(target != null && payEner(Cost.color(CardColor.WHITE, 1)))
                {
                    addToHand(target);
                }
            }
        }
    }
}
