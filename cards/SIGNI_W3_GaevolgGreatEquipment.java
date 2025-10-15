package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_W3_GaevolgGreatEquipment extends Card {
    
    public SIGNI_W3_GaevolgGreatEquipment()
    {
        setImageSets("WXDi-P05-032", "SPDi02-17");
        
        setOriginalName("大装　ゲイヴォルグ");
        setAltNames("タイソウゲイヴォルグ Taisou Geivorugu");
        setDescription("jp",
                "@C：あなたのセンタールリグは@>@U $T1：このルリグがアタックしたとき、対戦相手のシグニ１体を対象とし、それをトラッシュに置く。@@を得る。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたのアップ状態の白のシグニ１体をダウンしてもよい。そうした場合、カードを１枚引く。"
        );
        
        setName("en", "Gae Bolg, Full Armed");
        setDescription("en",
                "@C: Your Center LRIG gains@>@U $T1: When this LRIG attacks, put target SIGNI on your opponent's field into its owner's trash.@@" +
                "@U: At the beginning of your attack phase, you may down an upped white SIGNI on your field. If you do, draw a card."
        );
        
        setName("en_fan", "Gaevolg, Great Equipment");
        setDescription("en_fan",
                "@C: Your center LRIG gains:" +
                "@>@U $T1: When this LRIG attacks, target 1 of your opponent's SIGNI, and put it into the trash.@@" +
                "@U: At the beginning of your attack phase, you may down 1 of your upped white SIGNI. If you do, draw 1 card."
        );
        
		setName("zh_simplified", "大装 刺穿死棘");
        setDescription("zh_simplified", 
                "@C :你的核心分身得到\n" +
                "@>@U $T1 :当这只分身攻击时，对战对手的精灵1只作为对象，将其放置到废弃区。@@\n" +
                "@U 你的攻击阶段开始时，可以把你的竖直状态的白色的精灵1只#D。这样做的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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
            
            registerConstantAbility(new TargetFilter().own().LRIG(), new AbilityGainModifier(this::onConstEffModGetSample));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            return attachedAuto;
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
            trash(target);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex source)
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.DOWN).own().SIGNI().withColor(CardColor.WHITE).upped()).get();
            
            if(down(cardIndex))
            {
                draw(1);
            }
        }
    }
}
