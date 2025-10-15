package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W2_PictureFrameHolyBeauty extends Card {
    
    public SIGNI_W2_PictureFrameHolyBeauty()
    {
        setImageSets("WXDi-P07-056");
        
        setOriginalName("聖美　ガクブチ");
        setAltNames("セイビガクブチ Seibi Gakubuchi");
        setDescription("jp",
                "@C：対戦相手のターンの間、このシグニがアップ状態であるかぎり、このシグニのパワーは＋4000される。\n" +
                "@U $T1：あなたのルリグ１体がアタックしたとき、あなたのシグニ１体を対象とし、それをアップする。"
        );
        
        setName("en", "Picture Frame, Blessed Beauty");
        setDescription("en",
                "@C: During your opponent's turn, as long as this SIGNI is upped, it gets +4000 power.\n" +
                "@U $T1: When a LRIG on your field attacks, up target SIGNI on your field."
        );
        
        setName("en_fan", "Picture Frame, Holy Beauty");
        setDescription("en_fan",
                "@C: During your opponent's turn, while this SIGNI is upped, this SIGNI gets +4000 power.\n" +
                "@U $T1: When your LRIG attacks, target 1 of your SIGNI, and up it."
        );
        
		setName("zh_simplified", "圣美 画框");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，这只精灵在竖直状态时，这只精灵的力量+4000。\n" +
                "@U $T1 :当你的分身1只攻击时，你的精灵1只作为对象，将其竖直。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() && !isState(CardStateFlag.DOWNED) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isLRIG(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.UP).own().SIGNI()).get();
            up(target);
        }
    }
}
