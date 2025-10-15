package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.stock.StockAbilityHarmony;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G3_DemeterVerdantAngelPrincess extends Card {
    
    public SIGNI_G3_DemeterVerdantAngelPrincess()
    {
        setImageSets("WXDi-P03-042");
        
        setOriginalName("翠天姫　デメテル");
        setAltNames("スイテンキデメテル Suitenki Demeteru");
        setDescription("jp",
                "=H 青のルリグ１体\n\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるシグニが持つ色が合計３種類以上ありこのシグニがアップ状態の場合、あなたの他のシグニ１体を対象とし、%G %Xを支払ってもよい。そうした場合、ターン終了時まで、それは[[ランサー]]を得る。\n" +
                "@U $T1：あなたのターンの間、あなたの他のシグニ１体がバニッシュされたとき、[[エナチャージ１]]をする。"
        );
        
        setName("en", "Demeter, Jade Angel Queen");
        setDescription("en",
                "=H One blue LRIG\n" +
                "@U: At the beginning of your attack phase, if you have three or more different colors among SIGNI on your field and this SIGNI is upped, you may pay %G %X. If you do, another target SIGNI on your field gains [[Lancer]] until end of turn.\n" +
                "@U $T1: During your turn, when another SIGNI on your field is vanished, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Demeter, Verdant Angel Princess");
        setDescription("en_fan",
                "=H 1 blue LRIG\n\n" +
                "@U: At the beginning of your attack phase, if there are 3 or more colors among SIGNI on your field and this SIGNI is upped, target 1 of your other SIGNI, and you may pay %G %X. If you do, until end of turn, it gains [[Lancer]].\n" +
                "@U $T1: During your turn, when 1 of your other SIGNI is banished, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "翠天姬 得墨忒耳");
        setDescription("zh_simplified", 
                "=H蓝色的分身1只\n" +
                "@U :你的攻击阶段开始时，你的场上的精灵持有颜色在合计3种类以上且这只精灵在竖直状态的场合，你的其他的精灵1只作为对象，可以支付%G%X。这样做的场合，直到回合结束时为止，其得到[[枪兵]]。\n" +
                "@U $T1 :你的回合期间，当你的其他的精灵1只被破坏时，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerStockAbility(new StockAbilityHarmony(1, new TargetFilter().withColor(CardColor.BLUE)));
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(!getCardIndex().getIndexedInstance().isState(CardStateFlag.DOWNED) &&
               CardAbilities.getColorsCount(getSIGNIOnField(getOwner())) >= 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().except(getCardIndex())).get();
                
                if(target != null && payEner(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)))
                {
                    attachAbility(target, new StockAbilityLancer(), ChronoDuration.turnEnd());
                }
            }
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && caller != getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            enerCharge(1);
        }
    }
}
