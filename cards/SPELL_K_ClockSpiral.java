package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SPELL_K_ClockSpiral extends Card {

    public SPELL_K_ClockSpiral()
    {
        setImageSets("WX25-P1-111");

        setOriginalName("クロック・スパイラル");
        setAltNames("クロックスパイラル Kurokku Supairaru");
        setDescription("jp",
                "あなたの＜怪異＞のシグニ１体を対象とし、ターン終了時まで、それは[[アサシン（パワー8000以下のシグニ）]]か@>@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。@@を得る。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。"
        );

        setName("en", "Clock Spiral");
        setDescription("en",
                "Target 1 of your <<Apparition>> SIGNI, and until end of turn, it gains [[Assassin (SIGNI with power 8000 or less)]] or" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power."
        );

		setName("zh_simplified", "时光·螺旋");
        setDescription("zh_simplified", 
                "你的<<怪異>>精灵1只作为对象，直到回合结束时为止，其得到[[暗杀（力量8000以下的精灵）]]或\n" +
                "@>@U :当这只精灵攻击时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.APPARITION)));
        }
        private void onSpellEff()
        {
            CardIndex target = spell.getTarget();
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.ASSASSIN, ActionHint.AUTO) == 1)
                {
                    attachAbility(target, new StockAbilityAssassin(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
                } else {
                    AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                    attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
                }
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexOpposite)
        {
            return cardIndexOpposite.getIndexedInstance().getPower().getValue() <= 8000 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) getAbility().getSourceCardIndex().getIndexedInstance().gainPower(target, -8000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
            if(target != null) gainPower(target, -15000, ChronoDuration.turnEnd());
        }
    }
}

