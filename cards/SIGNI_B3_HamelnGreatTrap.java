package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B3_HamelnGreatTrap extends Card {
    
    public SIGNI_B3_HamelnGreatTrap()
    {
        setImageSets("WXDi-P05-037");
        
        setOriginalName("大罠　ハーメルン");
        setAltNames("ダイビンハーメルン Daibin Haamerun");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手は手札を２枚捨ててもよい。そうした場合、このアタックを無効にする。\n" +
                "@U：あなたのアタックフェイズ開始時、このシグニの正面のシグニ１体を対象とし、%B %Bを支払ってもよい。そうした場合、それを裏向きにする。このターン終了時、この方法で裏向きにしたシグニを、同じ場所にシグニがない場合、表向きにする。同じ場所にシグニがある場合、トラッシュに置く。"
        );
        
        setName("en", "Hameln, Master Trickster");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, your opponent may discard two cards. If they do, negate the attack.\n" +
                "@U: At the beginning of your attack phase, you may pay %B %B. If you do, turn target SIGNI in front of this SIGNI face down. At the end of this turn, if a SIGNI is not in the same position as the SIGNI turned face down this way, turn that SIGNI face up. However, if there is a SIGNI in the same position, put the SIGNI turned face down this way into the trash."
        );
        
        setName("en_fan", "Hameln, Great Trap");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, your opponent may discard 2 cards from their hand. If they do, disable that attack.\n" +
                "@U: At the beginning of your attack phase, target 1 SIGNI in front of this SIGNI, and you may pay %B %B. If you do, turn it face down. At the end of this turn, if there are no SIGNI in the same zone as the SIGNI turned face down this way, turn it face up. If there is a SIGNI in the same SIGNI zone, put it into the trash."
        );
        
		setName("zh_simplified", "大罠 哈默林");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手可以把手牌2张舍弃。这样做的场合，这次攻击无效。\n" +
                "@U :你的攻击阶段开始时，这只精灵的正面的精灵1只作为对象，可以支付%B %B。这样做的场合，将其变为里向。这个回合结束时，这个方法里向的精灵在，相同场所没有精灵的场合，变为表向。相同场所有精灵的场合，放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
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
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }
        
        private void onAutoEff1()
        {
            if(discard(getOpponent(), 0,2, ChoiceLogic.BOOLEAN).size() == 2)
            {
                disableNextAttack(getCardIndex());
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FLIP).OP().SIGNI().match(getOppositeSIGNI())).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLUE, 2)))
            {
                flip(target, CardFace.BACK);
                
                callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                    if(CardLocation.isSIGNI(target.getLocation()) && !flip(target, CardFace.FRONT))
                    {
                        trash(target);
                    }
                });
            }
        }
    }
}
