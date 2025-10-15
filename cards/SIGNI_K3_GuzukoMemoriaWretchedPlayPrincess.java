package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.events.EventDamage;

public final class SIGNI_K3_GuzukoMemoriaWretchedPlayPrincess extends Card {
    
    public SIGNI_K3_GuzukoMemoriaWretchedPlayPrincess()
    {
        setImageSets("WXDi-P07-047", "WXDi-P07-047P");
        
        setOriginalName("惨之遊姫　グズ子//メモリア");
        setAltNames("サンノユウキグズコメモリア Sannoyuuki Guzuko Memoria");
        setDescription("jp",
                "@U $T1：対戦相手がダメージを受けたとき、%K %K %K %X %Xを支払ってもよい。そうした場合、対戦相手のライフクロス１枚をクラッシュする。\n" +
                "@U $T1：あなたのターンの間、あなたのトラッシュからあなたのシグニ１体が場に出たとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。\n" +
                "@A $T1 #C #C #C：あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。"
        );
        
        setName("en", "Guzuko//Memoria, Tragic Party Queen");
        setDescription("en",
                "@U $T1: When your opponent takes damage, you may pay %K %K %K %X %X. If you do, crush one of your opponent's Life Cloth.\n" +
                "@U $T1: During your turn, when a SIGNI enters your field from your trash, target SIGNI on your opponent's field gets --3000 power until end of turn.\n" +
                "@A $T1 #C #C #C: Put target SIGNI from your trash onto your field."
        );
        
        setName("en_fan", "Guzuko//Memoria, Wretched Play Princess");
        setDescription("en_fan",
                "@U $T1: When your opponent takes damage, you may pay %K %K %K %X %X. If you do, crush 1 of your opponent's life cloth.\n" +
                "@U $T1: During your turn, when a SIGNI enters your field from the trash, target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power.\n" +
                "@A $T1 #C #C #C: Target 1 SIGNI from your trash, and put it onto the field."
        );
        
		setName("zh_simplified", "惨之游姬 迟钝子//回忆");
        setDescription("zh_simplified", 
                "@U $T1 :当对战对手受到伤害时，可以支付%K %K %K%X %X。这样做的场合，对战对手的生命护甲1张击溃。\n" +
                "@U $T1 :你的回合期间，当从你的废弃区把你的精灵1只出场时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n" +
                "@A $T1 #C #C #C:从你的废弃区把精灵1张作为对象，将其出场。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
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
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.DAMAGE, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.ENTER, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act = registerActionAbility(new CoinCost(3), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return ((EventDamage)getEvent()).getPlayer() != getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(payEner(Cost.color(CardColor.BLACK, 3) + Cost.colorless(2)))
            {
                crush(getOpponent());
            }
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) &&
                   caller.getOldLocation() == CardLocation.TRASH ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -3000, ChronoDuration.turnEnd());
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable()).get();
            putOnField(target);
        }
    }
}
