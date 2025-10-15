package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_B3_DraconidsNaturalStar extends Card {
    
    public SIGNI_B3_DraconidsNaturalStar()
    {
        setImageSets("WXDi-D05-017");
        
        setOriginalName("羅星　ジャコビニ");
        setAltNames("ラセイジャコビニ Rasei Jakobini");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このシグニの正面のシグニが凍結状態の場合、%B %B %Xを支払ってもよい。そうした場合、ターン終了時まで、このシグニは[[アサシン]]を得る。"
        );
        
        setName("en", "Giacobinids, Natural Planet");
        setDescription("en",
                "@U: At the beginning of your attack phase, if the SIGNI in front of this SIGNI is frozen, you may pay %B %B %X. If you do, this SIGNI gains [[Assassin]] until end of turn."
        );
        
        setName("en_fan", "Draconids, Natural Star");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if the SIGNI in front of this SIGNI is frozen, you may pay %B %B %X. If you do, until end of turn, this SIGNI gains [[Assassin]]."
        );
        
		setName("zh_simplified", "罗星 天龙座流星雨");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这只精灵的正面的精灵是冻结状态的场合，可以支付%B %B%X。这样做的场合，直到回合结束时为止，这只精灵得到[[暗杀]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = getCardIndex().getIndexedInstance().getOppositeSIGNI();
            
            if(cardIndex != null && cardIndex.getIndexedInstance().isState(CardStateFlag.FROZEN) &&
               payEner(Cost.color(CardColor.BLUE, 2) + Cost.colorless(1)))
            {
                attachAbility(getCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
            }
        }
    }
}
