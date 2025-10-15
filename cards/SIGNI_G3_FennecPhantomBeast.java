package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G3_FennecPhantomBeast extends Card {
    
    public SIGNI_G3_FennecPhantomBeast()
    {
        setImageSets("WXDi-P02-079");
        
        setOriginalName("幻獣　フェネック");
        setAltNames("ゲンジュウフェネック Genjuu Fenekku");
        setDescription("jp",
                "@U：各アタックフェイズ開始時、シグニ１体を対象とし、ターン終了時まで、このシグニの基本パワーはそれのパワーと同じ値になる。\n" +
                "@U $T1：あなたのパワー15000以上のシグニ１体がアタックしたとき、[[エナチャージ１]]をする。" +
                "~#：[[エナチャージ１]]をする。このターン、次にシグニがアタックしたとき、そのアタックを無効にする。"
        );
        
        setName("en", "Fennec, Phantom Terra Beast");
        setDescription("en",
                "@U: At the beginning of each attack phase, this SIGNI's base power becomes equal to target SIGNI's power until end of turn.\n" +
                "@U $T1: When a SIGNI on your field with power 15000 or more attacks, [[Ener Charge 1]]." +
                "~#[[Ener Charge 1]]. When the next SIGNI attacks this turn, negate the attack."
        );
        
        setName("en_fan", "Fennec, Phantom Beast");
        setDescription("en_fan",
                "@U: At the beginning of each player's attack phase, target 1 SIGNI, and until end of turn, this SIGNI's base power becomes the same as its power.\n" +
                "@U $T1: When 1 of your SIGNI with power 15000 or more attacks, [[Ener Charge 1]]." +
                "~#[[Ener Charge 1]]. This turn, the next time a SIGNI attacks, disable that attack."
        );
        
		setName("zh_simplified", "幻兽 耳廓狐");
        setDescription("zh_simplified", 
                "@U :各攻击阶段开始时，精灵1只作为对象，直到回合结束时为止，这只精灵的基本力量变为与其的力量相同的数值。\n" +
                "@U $T1 :当你的力量15000以上的精灵1只攻击时，[[能量填充1]]。" +
                "~#[[能量填充1]]。这个回合，当下一次精灵攻击时，那次攻击无效。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
         
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.COPY).SIGNI()).get();
            if(target != null) setBasePower(getCardIndex(), target.getIndexedInstance().getPower().getValue(), ChronoDuration.turnEnd());
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return isOwnCard(caller) && CardLocation.isSIGNI(caller.getLocation()) &&
                   getEvent().getCaller().getPower().getValue() >= 15000 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            enerCharge(1);
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_BE_ATTACKED, getOwner(), record, data -> {
                if(!CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType())) return RuleCheckState.IGNORE;
                
                record.forceExpire();
                
                return RuleCheckState.BLOCK;
            });
        }
    }
}
