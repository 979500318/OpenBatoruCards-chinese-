package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B1_ManominAzureDevil extends Card {
    
    public SIGNI_B1_ManominAzureDevil()
    {
        setImageSets("WXDi-P05-061", "SPDi01-76");
        
        setOriginalName("蒼魔　マノミン");
        setAltNames("ソウママノミン Souma Manomin");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、手札を１枚捨ててもよい。そうした場合、対戦相手は手札を１枚捨てる。"
        );
        
        setName("en", "Manomin, Azure Evil");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may discard a card. If you do, your opponent discards a card."
        );
        
        setName("en_fan", "Manomin, Azure Devil");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, you may discard 1 card from your hand. If you do, your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "苍魔 粉双带");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以把手牌1张舍弃。这样做的场合，对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                discard(getOpponent(), 1);
            }
        }
    }
}
