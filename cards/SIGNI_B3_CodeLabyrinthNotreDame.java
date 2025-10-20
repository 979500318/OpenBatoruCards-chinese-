package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B3_CodeLabyrinthNotreDame extends Card {
    
    public SIGNI_B3_CodeLabyrinthNotreDame()
    {
        setImageSets("WXDi-P04-038");
        
        setOriginalName("コードラビリンス　ノートルダム");
        setAltNames("コードラビリンスノートルダム Koodo Rabirinsu Nootorudamu");
        setDescription("jp",
                "@U $T1：メインフェイズとアタックフェイズの間、対戦相手がカードを１枚引いたとき、対戦相手は手札を１枚捨てる。\n" +
                "@U：このシグニがバニッシュされたとき、手札を２枚捨ててもよい。そうした場合、このシグニをエナゾーンからダウン状態で場に出す。" +
                "~#：対戦相手のシグニ１体を対象とし、手札を２枚捨ててもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Notre Dame, Code: Labyrinth");
        setDescription("en",
                "@U $T1: During the main phase and attack phase, when your opponent draws a card, they discard a card.\n" +
                "@U: When this SIGNI is vanished, you may discard two cards. If you do, put this SIGNI from your Ener Zone onto your field downed." +
                "~#You may discard two cards. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Code Labyrinth Notre Dame");
        setDescription("en_fan",
                "@U $T1: During the main phase and attack phase, whenever your opponent draws 1 card, your opponent discards 1 card from their hand.\n" +
                "@U: When this SIGNI is banished, you may discard 2 cards from your hand. If you do, put this SIGNI from the ener zone onto the field downed." +
                "~#Target 1 of your opponent's SIGNI, and you may discard 2 cards from your hand. If you do, banish it."
        );
        
		setName("zh_simplified", "迷牢代号 圣母院");
        setDescription("zh_simplified", 
                "@U $T1 :主要阶段和攻击阶段期间，当对战对手抽1张牌时，对战对手把手牌1张舍弃。\n" +
                "@U :当这只精灵被破坏时，可以把手牌2张舍弃。这样做的场合，这张精灵从能量区以横置状态出场。" +
                "~#对战对手的精灵1只作为对象，可以把手牌2张舍弃。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.DRAW, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return (getCurrentPhase() == GamePhase.MAIN || GamePhase.isAttackPhase(getCurrentPhase())) && !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }
        
        private void onAutoEff2()
        {
            if(getCardIndex().getLocation() == CardLocation.ENER && discard(0,2, ChoiceLogic.BOOLEAN).get() != null)
            {
                putOnField(getCardIndex(), Enter.DOWNED);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && discard(0,2, ChoiceLogic.BOOLEAN).get() != null)
            {
                banish(target);
            }
        }
    }
}
