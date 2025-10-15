package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B1_PlanktonNaturalBacteria extends Card {
    
    public SIGNI_B1_PlanktonNaturalBacteria()
    {
        setImageSets("WXDi-P04-065");
        
        setOriginalName("羅菌　プランクトン");
        setAltNames("ラキンプランクトン Rakin Purankuton");
        setDescription("jp",
                "@U $T1：対戦相手のシグニ１体が凍結状態になったとき、ターン終了時まで、そのシグニのパワーを－1000する。"
        );
        
        setName("en", "Plankton, Natural Bacteria");
        setDescription("en",
                "@U $T1: When a SIGNI on your opponent's field becomes frozen, that SIGNI gets --1000 power until end of turn."
        );
        
        setName("en_fan", "Plankton, Natural Bacteria");
        setDescription("en_fan",
                "@U $T1: When 1 of your opponent's SIGNI becomes frozen, until end of turn, that SIGNI gets --1000 power."
        );
        
		setName("zh_simplified", "罗菌 浮游生物");
        setDescription("zh_simplified", 
                "@U $T1 :当对战对手的精灵1只变为冻结状态时，直到回合结束时为止，那只精灵的力量-1000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
        setLevel(1);
        setPower(1000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.FREEZE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            auto.enableEventSourceSelection();
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(caller, -1000, ChronoDuration.turnEnd());
        }
    }
}
