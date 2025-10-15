package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W1_YaekoriSmallEquipment extends Card {
    
    public SIGNI_W1_YaekoriSmallEquipment()
    {
        setImageSets("WXDi-P05-045");
        setLinkedImageSets("WXDi-P02-035");
        
        setOriginalName("小装　ヤエコリ");
        setAltNames("ショウソウヤエコリ Shousou Yaekori");
        setDescription("jp",
                "@C：あなたの場に《大装　ヤエキリ》があるかぎり、このシグニのパワーは＋5000され、このシグニは@>@U：各アタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。@@を得る。\n" +
                "@E：あなたの白のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋2000する。"
        );
        
        setName("en", "Yaekori, Lightly Armed");
        setDescription("en",
                "@C: As long as there is \"Yaekiri, Full Armed\" on your field, this SIGNI gets +5000 power and gains@>@U: At the beginning of each attack phase, target SIGNI on your opponent's field loses its abilities until end of turn.@@" +
                "@E: Target white SIGNI on your field gets +2000 power until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Yaekori, Small Equipment");
        setDescription("en_fan",
                "@C: As long as there is a \"Yaekiri, Great Equipment\" on your field, this SIGNI gets +5000 power, and it gains:" +
                "@>@U: At the beginning of each attack phase, target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities.@@" +
                "@E: Target 1 of your white SIGNI, and until the end of your opponent's next turn, it gets +2000 power."
        );
        
		setName("zh_simplified", "小装 八重丝");
        setDescription("zh_simplified", 
                "@C :你的场上有《大装　ヤエキリ》时，这只精灵的力量+5000，这只精灵得到\n" +
                "@>@U :各攻击阶段开始时，对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去。@@\n" +
                "@E :你的白色的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000),new AbilityGainModifier(this::onConstEffModGetSample));
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().withName("大装　ヤエキリ").getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
            disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withColor(CardColor.WHITE)).get();
            gainPower(target, 2000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
