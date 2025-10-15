package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class LRIGA_W2_RememberProphecy extends Card {

    public LRIGA_W2_RememberProphecy()
    {
        setImageSets("WXDi-P15-035");

        setOriginalName("リメンバ・プロフェシー");
        setAltNames("リメンバプロフェシー Rimenba Purofeshii");
        setDescription("jp",
                "@E：対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらは@>@C：アタックできない。@@を得る。\n" +
                "@E %X %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。"
        );

        setName("en", "Remember Prophecy");
        setDescription("en",
                "@E: Up to two target SIGNI on your opponent's field gain@>@C: This SIGNI cannot attack.@@until end of turn.\n@E %X %X: Target SIGNI on your opponent's field loses its abilities until end of turn."
        );
        
        setName("en_fan", "Remember Prophecy");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's SIGNI, and until end of turn, they gain:" +
                "@>@C: Can't attack.@@" +
                "@E %X %X: Target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities."
        );

		setName("zh_simplified", "忆·预言");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵2只最多作为对象，直到回合结束时为止，这些得到\n" +
                "@>@C :不能攻击。@@\n" +
                "@E %X %X:对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.REMEMBER);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(4));
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
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().SIGNI());
            if(data.get() != null) for(int i=0;i<data.size();i++) attachAbility(data.get(i), new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
            disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
    }
}
