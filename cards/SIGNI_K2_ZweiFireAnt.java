package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K2_ZweiFireAnt extends Card {
    
    public SIGNI_K2_ZweiFireAnt()
    {
        setImageSets("WXDi-P04-083");
        
        setOriginalName("ツヴァイ＝ヒアリ");
        setAltNames("ツヴァイヒアリ Tsuvai Hiari");
        setDescription("jp",
                "@C：あなたのターンの間、このシグニの正面のシグニのパワーをそのシグニのレベル１につき－1000する。"
        );
        
        setName("en", "Fire Ant Type: Zwei");
        setDescription("en",
                "@C: During your turn, the SIGNI in front of this SIGNI gets --1000 power for each of its levels."
        );
        
        setName("en_fan", "Zwei-Fire Ant");
        setDescription("en_fan",
                "@C: During your turn, the SIGNI in front of this SIGNI gets --1000 power for each level it has."
        );
        
		setName("zh_simplified", "ZWEI=火蚁");
        setDescription("zh_simplified", 
                "@C $TO :这只精灵的正面的精灵的力量依据那只精灵的等级的数量，每有1级就-1000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffSharedCond,
                new TargetFilter().OP().SIGNI(),
                new PowerModifier(this::onConstEffSharedModGetValue)
            );
        }
        
        private ConditionState onConstEffSharedCond(CardIndex cardIndex)
        {
            return isOwnTurn() && cardIndex == getOppositeSIGNI() ? ConditionState.OK : ConditionState.BAD;
        }
        private double onConstEffSharedModGetValue(CardIndex cardIndex)
        {
            return getOppositeSIGNI() != null ? -1000 * getOppositeSIGNI().getIndexedInstance().getLevel().getValue() : 0;
        }
    }
}
