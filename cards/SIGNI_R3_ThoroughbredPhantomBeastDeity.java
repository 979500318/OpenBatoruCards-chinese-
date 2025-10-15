package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R3_ThoroughbredPhantomBeastDeity extends Card {
    
    public SIGNI_R3_ThoroughbredPhantomBeastDeity()
    {
        setImageSets("WXDi-P07-042");
        
        setOriginalName("幻獣神　サラブレッド");
        setAltNames("ゲンジュウシンサラブレッド Genjuushin Sarabureddo");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、ターン終了時まで、あなたのすべてのシグニのパワーを＋3000する。\n" +
                "@U：あなたのターン終了時、あなたの場にあるシグニのパワーの合計が30000以上の場合、カードを１枚引く。\n" +
                "@E %R %X：あなたのトラッシュからレベル２以下の＜地獣＞のシグニ１枚を対象とし、それを場に出す。ターン終了時まで、それのパワーを＋4000する。" +
                "~#：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Thoroughbred, Phantom Beast Deity");
        setDescription("en",
                "@U: At the beginning of your attack phase, all SIGNI on your field get +3000 power until end of turn.\n" +
                "@U: At the end of your turn, if the total power of SIGNI on your field is 30000 or more, draw a card.\n" +
                "@E %R %X: Put target level two or less <<Terra Beast>> SIGNI from your trash onto your field. It gets +4000 power until end of turn." +
                "~#Vanish target SIGNI on your opponent's field with power 10000 or less."
        );
        
        setName("en_fan", "Thoroughbred, Phantom Beast Deity");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, until end of turn, all of your SIGNI get +3000 power.\n" +
                "@U: At the end of your turn, if the total power of all of your SIGNI is 30000 or more, draw 1 card.\n" +
                "@E %R %X: Target 1 level 2 or lower <<Earth Beast>> SIGNI from your trash, and put it onto the field. Until end of turn, it gets +4000 power." +
                "~#Target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );
        
		setName("zh_simplified", "幻兽神 纯种马");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，直到回合结束时为止，你的全部的精灵的力量+3000。\n" +
                "@U :你的回合结束时，你的场上的精灵的力量的合计在30000以上的场合，抽1张牌。\n" +
                "@E %R%X:从你的废弃区把等级2以下的<<地兽>>精灵1张作为对象，将其出场。直到回合结束时为止，其的力量+4000。" +
                "~#对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            gainPower(getSIGNIOnField(getOwner()), 3000, ChronoDuration.turnEnd());
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().getExportedData().stream().mapToDouble(c -> ((CardIndex)c).getIndexedInstance().getPower().getValue()).sum() >= 30000)
            {
                draw(1);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(0,2).withClass(CardSIGNIClass.EARTH_BEAST).fromTrash().playable()).get();
            putOnField(target);
            gainPower(target, 4000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }
    }
}
