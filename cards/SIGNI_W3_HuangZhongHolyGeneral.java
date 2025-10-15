package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W3_HuangZhongHolyGeneral extends Card {
    
    public SIGNI_W3_HuangZhongHolyGeneral()
    {
        setImageSets("WXDi-P04-050");
        
        setOriginalName("聖将　コウチュウ");
        setAltNames("セイショウコウチュウ Seishou Kouchyuu");
        setDescription("jp",
                "@C：このシグニがアップ状態であるかぎり、このシグニのパワーは＋5000される。\n" +
                "@C：このシグニがアップ状態であるかぎり、このシグニの隣にあるあなたのシグニのパワーを＋3000する。"
        );
        
        setName("en", "Huang Zhong, Blessed General");
        setDescription("en",
                "@C: As long as this SIGNI is upped, it gets +5000 power.\n" +
                "@C: As long as this SIGNI is upped, SIGNI on your field next to this SIGNI get +3000 power."
        );
        
        setName("en_fan", "Huang Zhong, Holy General");
        setDescription("en_fan",
                "@C: As long as this SIGNI is upped, this SIGNI gets +5000 power.\n" +
                "@C: As long as this SIGNI is upped, SIGNI next to this SIGNI get +3000 power."
        );
        
		setName("zh_simplified", "圣将 黄忠");
        setDescription("zh_simplified", 
                "@C :这只精灵在竖直状态时，这只精灵的力量+5000。\n" +
                "@C :这只精灵在竖直状态时，这只精灵的相邻的你的精灵的力量+3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
            registerConstantAbility(this::onConstEffSharedCond, new TargetFilter().own().SIGNI().except(cardId), new PowerModifier(3000));
        }
        
        private ConditionState onConstEffCond()
        {
            return !isState(CardStateFlag.DOWNED) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onConstEffSharedCond(CardIndex cardIndex)
        {
            return !isState(CardStateFlag.DOWNED) &&
                   isSIGNINextToSIGNI(cardIndex, getCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
