package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_B3_RoyalBlueNaturalPyroxene extends Card {
    
    public SIGNI_B3_RoyalBlueNaturalPyroxene()
    {
        setImageSets("WXDi-P02-041");
        
        setOriginalName("羅輝石　ロイヤルブルー");
        setAltNames("ラキセキロイヤルブルー Rakiseki Roiyaru Buruu");
        setDescription("jp",
                "@A $T1 %B0：対戦相手の凍結状態のシグニ１体を対象とし、ターン終了時まで、それは@>@U：アタックフェイズ開始時、あなたが%X %Xを支払わないかぎり、このシグニをバニッシュする。@@を得る。" +
                "~#：対戦相手のルリグ１体と対戦相手のシグニ１体を対象とし、それらを凍結する。"
        );
        
        setName("en", "Royal Blue, Natural Crystal Brilliance");
        setDescription("en",
                "@A $T1 %B0: Target frozen SIGNI on your opponent's field gains@>@U: At the beginning of the attack phase, vanish this SIGNI unless you pay %X %X.@@until end of turn." +
                "~#Freeze target LRIG and target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Royal Blue, Natural Pyroxene");
        setDescription("en_fan",
                "@A $T1 %B0: Target 1 of your opponent's frozen SIGNI, and until end of turn, it gains:" +
                "@>@U: At the beginning of the attack phase, unless you pay %X %X, banish this SIGNI.@@" +
                "~#Target 1 of your opponent's LRIGs and 1 of your opponent's SIGNI, and freeze them."
        );
        
		setName("zh_simplified", "罗辉石 皇家蔚蓝石");
        setDescription("zh_simplified", 
                "@A $T1 %B0:对战对手的冻结状态的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U 攻击阶段开始时，如果你不把%X %X:支付，那么这只精灵破坏。@@" +
                "~#对战对手的分身1只和对战对手的精灵1只作为对象，将这些冻结。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI().withState(CardStateFlag.FROZEN)).get();
            
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex cardIndexSource = getAbility().getSourceCardIndex();
            if(CardLocation.isSIGNI(cardIndexSource.getLocation()) && !cardIndexSource.getIndexedInstance().payEner(Cost.colorless(2)))
            {
                cardIndexSource.getIndexedInstance().banish(cardIndexSource);
            }
        }
        
        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = new DataTable<>();
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
            if(target != null) data.add(target);
            
            target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            if(target != null) data.add(target);
            
            freeze(data);
        }
    }
}
