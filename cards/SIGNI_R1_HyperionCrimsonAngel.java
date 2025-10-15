package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R1_HyperionCrimsonAngel extends Card {
    
    public SIGNI_R1_HyperionCrimsonAngel()
    {
        setImageSets("WXDi-P07-060");
        
        setOriginalName("紅天　ヒュペリオン");
        setAltNames("ソウテンヒュペリオン Souten Hyuperion");
        setDescription("jp",
                "@C：このシグニが覚醒状態であるかぎり、このシグニのパワーは＋2000され、このシグニは@>@U：このシグニがアタックしたとき、対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。@@を得る。\n" +
                "@U：あなたのターン終了時、このシグニは覚醒する。"
        );
        
        setName("en", "Hyperion, Crimson Angel");
        setDescription("en",
                "@C: As long as this SIGNI is awakened, this SIGNI gets +2000 power and gains@>@U: Whenever this SIGNI attacks, vanish target SIGNI on your opponent's field with power 3000 or less.@@" +
                "@U: At the end of your turn, this SIGNI is awakened. "
        );
        
        setName("en_fan", "Hyperion, Crimson Angel");
        setDescription("en_fan",
                "@C: As long as this SIGNI is awakened, this SIGNI gets +2000 power, and it gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power 3000 or less, and banish it.@@" +
                "@U: At the end of your turn, this SIGNI awakens."
        );
        
		setName("zh_simplified", "红天 许珀里翁");
        setDescription("zh_simplified", 
                "@C :这只精灵在觉醒状态时，这只精灵的力量+2000，这只精灵得到\n" +
                "@>@C :当这只精灵攻击时，对战对手的力量3000以下的精灵1只作为对象，将其破坏。@@\n" +
                "@U :你的回合结束时，这只精灵觉醒。（精灵觉醒后在场上保持觉醒状态）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(2000), new AbilityGainModifier(this::onConstEffModGetSample));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return isState(CardStateFlag.AWAKENED) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
            banish(target);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            getCardStateFlags().addValue(CardStateFlag.AWAKENED);
        }
    }
}
