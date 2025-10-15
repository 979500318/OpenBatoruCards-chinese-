package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R2_HotarumaruMediumEquipment extends Card {
    
    public SIGNI_R2_HotarumaruMediumEquipment()
    {
        setImageSets("WXDi-P00-053");
        
        setOriginalName("中装　ホタルマル");
        setAltNames("チュウソウホタルマル Chuusoo Hotarumaru");
        setDescription("jp",
                "@C：あなたのターンの間、このシグニの隣にあるシグニのパワーを＋３０００する。"
        );
        
        setName("en", "Hotarumaru, High Armed");
        setDescription("en",
                "@C: During your turn, SIGNI on your field next to this SIGNI get +3000 power."
        );
        
        setName("en_fan", "Hotarumaru, Medium Equipment");
        setDescription("en_fan",
                "@C: During your turn, your SIGNI next to this SIGNI get +3000 power."
        );
        
		setName("zh_simplified", "中装 萤丸大太刀");
        setDescription("zh_simplified", 
                "@C :你的回合期间，这只精灵的相邻的你的精灵的力量+3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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
            
            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI(), new PowerModifier(3000));
        }
        
        private ConditionState onConstEffCond(CardIndex cardIndex)
        {
            return isOwnTurn() && isSIGNINextToSIGNI(cardIndex, getCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
