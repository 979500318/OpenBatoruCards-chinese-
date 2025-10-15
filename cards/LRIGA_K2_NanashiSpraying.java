package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_NanashiSpraying extends Card {

    public LRIGA_K2_NanashiSpraying()
    {
        setImageSets("WX25-P1-051");

        setOriginalName("ナナシ・噴霧");
        setAltNames("ナナシフンム Nanashi Funmu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、ターン終了時まで、このシグニのパワーを－10000する。@@を得る。\n" +
                "@E %X：対戦相手の感染状態のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。"
        );

        setName("en", "Nanashi Spraying");
        setDescription("en",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, until end of turn, this SIGNI gets --10000 power.@@" +
                "@E %X: Target 1 of your opponent's infected SIGNI, and until end of turn, it loses its abilities."
        );

		setName("zh_simplified", "无名·喷雾");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U :当这只精灵攻击时，直到回合结束时为止，这只精灵的力量-10000。@@\n" +
                "@E %X:对战对手的感染状态的精灵1只作为对象，直到回合结束时为止，其的能力失去。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.NANASHI);
        setColor(CardColor.BLACK);
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
            getAbility().getSourceCardIndex().getIndexedInstance().gainPower(getAbility().getSourceCardIndex(), -10000, ChronoDuration.turnEnd());
        }

        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI().infected()).get();
            disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
    }
}
