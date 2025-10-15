package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G2_BluePassionflowerNaturalPlant extends Card {
    
    public SIGNI_G2_BluePassionflowerNaturalPlant()
    {
        setImageSets("WXDi-P05-074");
        
        setOriginalName("羅植　トケイソウ");
        setAltNames("ラショクトケイソウ Rashoku Tokeisou");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのエナゾーンからあなたのセンタールリグと共通する色を持つシグニを１枚まで対象とし、それを手札に加える。"
        );
        
        setName("en", "Passionflower, Natural Plant");
        setDescription("en",
                "@U: At the beginning of your attack phase, add up to one target SIGNI that shares a color with your center LRIG from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Blue Passionflower, Natural Plant");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target up to 1 SIGNI from your ener zone that shares a common color with your center LRIG, and add it to your hand."
        );
        
		setName("zh_simplified", "罗植 时计草");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从你的能量区把持有与你的核心分身共通颜色的精灵1张最多作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromEner()).get();
            addToHand(target);
        }
    }
}
