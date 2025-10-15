package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class ARTS_X_SmokeSlipStop extends Card {

    public ARTS_X_SmokeSlipStop()
    {
        setImageSets("WX25-P2-047");

        setOriginalName("スモーク・スリップ・ストップ");
        setAltNames("スモークスリップストップ Sumooku Surippu Sutoppu");
        setDescription("jp",
                "対戦相手のルリグ１体と対戦相手のシグニ１体を対象とし、ターン終了時まで、それらは@>@C：アタックできない。@@を得る。"
        );

        setName("en", "Smoke Slip Stop");
        setDescription("en",
                "Target 1 of your opponent's LRIG and 1 of your opponent's SIGNI, and until end of turn, they gain:@>@C: Can't attack."
        );

		setName("zh_simplified", "恶瘴·跌倒·阻碍");
        setDescription("zh_simplified", 
                "对战对手的分身1只和对战对手的精灵1只作为对象，直到回合结束时为止，这些得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.ARTS);
        setCost(Cost.colorless(4));
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
            DataTable<CardIndex> data = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().anyLRIG());
            data.add(playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get());
            if(data.get() != null) for(int i=0;i<data.size();i++) attachAbility(data.get(i), new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}

