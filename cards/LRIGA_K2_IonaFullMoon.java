package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.AttackModifierFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_IonaFullMoon extends Card {

    public LRIGA_K2_IonaFullMoon()
    {
        setImageSets("WXDi-P13-035");

        setOriginalName("イオナ・フルムーン");
        setAltNames("イオナフルムーン Iona Furu Muun");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、ターン終了時まで、このシグニのパワーを－12000する。@@を得る。\n" +
                "@E %X：対戦相手のシグニ１体を対象とし、このターン、それは可能ならばアタックしなければならない。"
        );

        setName("en", "Iona Full Moon");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gains@>@U: Whenever this SIGNI attacks, it gets --12000 power until end of turn.@@until end of turn.\n@E %X: Target SIGNI on your opponent's field must attack if able this turn. ((It must attack before any other SIGNI.))"
        );
        
        setName("en_fan", "Iona Full Moon");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, until end of turn, it gets --12000 power.@@" +
                "@E %X: Target 1 of your opponent's SIGNI, and this turn, it must attack if able."
        );

		setName("zh_simplified", "伊绪奈·满月");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U :当这只精灵攻击时，直到回合结束时为止，这只精灵的力量-12000。@@\n" +
                "@E %X:对战对手的精灵1只作为对象，这个回合，其如果能攻击，则必须攻击。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.IONA);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().gainPower(getAbility().getSourceCardIndex(), -12000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            
            if(target != null)
            {
                target.getIndexedInstance().gainValue(target, target.getIndexedInstance().getAttackModifierFlags(),AttackModifierFlag.FORCE_ATTACK, ChronoDuration.turnEnd());
            }
        }
    }
}
