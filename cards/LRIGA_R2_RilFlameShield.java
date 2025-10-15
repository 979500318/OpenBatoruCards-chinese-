package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.AttackModifierFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AttackModifier;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class LRIGA_R2_RilFlameShield extends Card {

    public LRIGA_R2_RilFlameShield()
    {
        setImageSets("WX25-P1-049");

        setOriginalName("リル・炎盾");
        setAltNames("リルエンジュン Riru Enjun");
        setDescription("jp",
                "@E：対戦相手のパワー20000以下のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "@E %X %X：対戦相手のパワー20000以下のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。"
        );

        setName("en", "Ril Flame Shield");
        setDescription("en",
                "@E: Target 1 of your opponent's SIGNI with power 20000 or less, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "@E %X %X: Target 1 of your opponent's SIGNI with power 20000 or less, and until end of turn, it gains:" +
                "@>@C: Can't attack."
        );

		setName("zh_simplified", "莉露·炎盾");
        setDescription("zh_simplified", 
                "@E :对战对手的力量20000以下的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "@E %X %X:对战对手的力量20000以下的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.RIL);
        setColor(CardColor.RED);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI().withPower(0,20000)).get();
            attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}
