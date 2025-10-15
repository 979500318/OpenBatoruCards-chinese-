package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G3_MammoPhantomBeast extends Card {
    
    public SIGNI_G3_MammoPhantomBeast()
    {
        setImageSets("WXDi-P08-073");
        
        setOriginalName("幻獣　マンモ");
        setAltNames("ゲンジュウマンモ Genjuu Manmo");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、ターン終了時まで、あなたのすべてのシグニのパワーを＋3000する。\n" +
                "@U $T2：あなたのパワー10000以上の＜地獣＞のシグニ１体がアタックしたとき、【エナチャージ１】をする。"
        );
        
        setName("en", "Mammo, Phantom Terra Beast");
        setDescription("en",
                "@U: At the beginning of your attack phase, all SIGNI on your field get +3000 power until end of turn.\n" +
                "@U $T2: Whenever a <<Terra Beast>> SIGNI on your field with power 10000 or more attacks, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Mammo, Phantom Beast");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, until end of turn, all of your SIGNI get +3000 power.\n" +
                "@U $T2: Whenever 1 of your <<Earth Beast>> SIGNI with power 10000 or more attacks, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "幻兽 猛犸");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，直到回合结束时为止，你的全部的精灵的力量+3000。\n" +
                "@U $T2 :当你的力量10000以上的<<地獣>>精灵1只攻击时，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 2);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            gainPower(getSIGNIOnField(getOwner()), 3000, ChronoDuration.turnEnd());
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) &&
                   getEvent().getCaller().getSIGNIClass().matches(CardSIGNIClass.EARTH_BEAST) &&
                   getEvent().getCaller().getPower().getValue() >= 10000 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            enerCharge(1);
        }
    }
}
