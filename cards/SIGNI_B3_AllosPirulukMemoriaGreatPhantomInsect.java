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
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_B3_AllosPirulukMemoriaGreatPhantomInsect extends Card {
    
    public SIGNI_B3_AllosPirulukMemoriaGreatPhantomInsect()
    {
        setImageSets("WXDi-P07-044", "WXDi-P07-044P");
        
        setOriginalName("大幻蟲　アロス・ピルルク//メモリア");
        setAltNames("ダイゲンチュウアロスピルルクメモリア Daigenchuu Arosu Piruruku Memoria");
        setDescription("jp",
                "@U $T1：あなたのメインフェイズの間、あなたがシグニを１枚捨てたとき、そのカードをトラッシュから場に出す。\n" +
                "@U $T1：あなたのターンの間、あなたのシグニ１体が手札以外の領域から場に出たとき、対戦相手のシグニ１体を対象とし、それを凍結し、ターン終了時まで、それのパワーを－2000する。\n" +
                "@A $T1 #C #C：対戦相手は手札を１枚捨てる。"
        );
        
        setName("en", "Allos Piruluk//Memoria, Great Insect");
        setDescription("en",
                "@U $T1: During your main phase, when you discard a SIGNI, put that card from your trash onto your field.\n" +
                "@U $T1: During your turn, when a SIGNI enters your field from a Zone other than a player's hand, freeze target SIGNI on your opponent's field and it gets --2000 power until end of turn.\n" +
                "@A $T1 #C #C: Your opponent discards a card."
        );
        
        setName("en_fan", "Allos Piruluk//Memoria, Great Phantom Insect");
        setDescription("en_fan",
                "@U $T1: Whenever you discard a SIGNI during your main phase, put that card from your trash onto the field.\n" +
                "@U $T1: Whenever a SIGNI enters your field from a zone other than the hand during your turn, target 1 of your opponent's SIGNI, and freeze it, and until end of turn, it gets --2000 power.\n" +
                "@A $T1 #C #C: Your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "大幻虫 阿洛斯·皮璐璐可//回忆");
        setDescription("zh_simplified", 
                "@U $T1 :你的主要阶段期间，当你把精灵1张舍弃时，那张牌从废弃区出场。\n" +
                "@U $TO $T1 :当你的精灵1只从手牌以外的领域出场时，对战对手的精灵1只作为对象，将其冻结，直到回合结束时为止，其的力量-2000。\n" +
                "@A $T1 #C #C:对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
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
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);
            auto1.enableEventSourceSelection();
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.ENTER, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act = registerActionAbility(new CoinCost(2), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && getCurrentPhase() == GamePhase.MAIN && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(caller.getLocation() == CardLocation.TRASH)
            {
                putOnField(caller);
            }
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) &&
                   CardType.isSIGNI(caller.getCardReference().getType()) && caller.getOldLocation() != CardLocation.HAND ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            
            freeze(target);
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
        
        private void onActionEff()
        {
            discard(getOpponent(), 1);
        }
    }
}
