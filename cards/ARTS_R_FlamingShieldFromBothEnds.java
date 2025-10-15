package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class ARTS_R_FlamingShieldFromBothEnds extends Card {

    public ARTS_R_FlamingShieldFromBothEnds()
    {
        setImageSets("WX24-D2-08", "SPDi37-08");

        setOriginalName("炎盾両面");
        setAltNames("エンジュンリョウメン Enjunryoumen");
        setDescription("jp",
                "対戦相手のパワー20000以下のシグニを２体まで対象とし、ターン終了時まで、それらは@>@C：アタックできない。@@を得る。"
        );

        setName("en", "Flaming Shield from Both Ends");
        setDescription("en",
                "Target up to 2 of your opponent's SIGNI with power 20000 or less, and until end of turn, they gain:" +
                "@>@C: Can't attack."
        );

		setName("zh_simplified", "炎盾两面");
        setDescription("zh_simplified", 
                "对战对手的力量20000以下的精灵2只最多作为对象，直到回合结束时为止，这些得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1) + Cost.colorless(2));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().SIGNI().withPower(0,20000));
            if(data.get() != null) for(int i=0;i<data.size();i++) attachAbility(data.get(i), new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}

