package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventPowerChanged;

public final class SIGNI_K3_DarkEnergeGreatEquipment extends Card {
    
    public SIGNI_K3_DarkEnergeGreatEquipment()
    {
        setImageSets("WXDi-P01-043");
        
        setOriginalName("大装　ダークエナジェ");
        setAltNames("タイソウダークエナジェ Taisou Daaku Enaje");
        setDescription("jp",
                "@U $T1：対戦相手のシグニのパワーが０以下になったとき、[[エナチャージ１]]をする。\n" +
                "@E %K：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。"
        );
        
        setName("en", "Dark Energie, Full Armed");
        setDescription("en",
                "@U $T1: Whenever the power of a SIGNI on your opponent's field becomes 0 or less, [[Ener Charge 1]].\n" +
                "@E %K: Target SIGNI on your opponent's field gets --5000 power until end of turn."
        );
        
        setName("en_fan", "Dark Energe, Great Equipment");
        setDescription("en_fan",
                "@U $T1: When the power of your opponent's SIGNI becomes 0 or less, [[Ener Charge 1]].\n" +
                "@E %K: Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power."
        );
        
		setName("zh_simplified", "大装 黑暗源能");
        setDescription("zh_simplified", 
                "@U $T1 :当对战对手的精灵的力量在0以下时，[[能量填充1]]。\n" +
                "@E %K:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.POWER_CHANGED, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex cardIndex)
        {
            return !isOwnCard(cardIndex) && EventPowerChanged.getDataNewValue() <= 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex cardIndex)
        {
            enerCharge(1);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -5000, ChronoDuration.turnEnd());
        }
    }
}
