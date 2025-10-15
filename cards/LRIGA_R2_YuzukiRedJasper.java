package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_R2_YuzukiRedJasper extends Card {

    public LRIGA_R2_YuzukiRedJasper()
    {
        setImageSets("WX24-P2-044");

        setOriginalName("遊月・レッドジャスパー");
        setAltNames("ユヅキレッドジャスパー Yuzuki Reddo Jasupaa");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、%X %X %Xを支払わないかぎり、このシグニをバニッシュする。@@を得る。\n" +
                "@E %R %X %X %X %X：対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらは@>@U：このシグニがアタックしたとき、%X %X %Xを支払わないかぎり、このシグニをバニッシュする。@@を得る。"
        );

        setName("en", "Yuzuki Red Jasper");
        setDescription("en",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, banish this SIGNI unless you pay %X %X %X.@@" +
                "@E %R %X %X %X %X: Target up to 2 of your opponent's SIGNI, and until end of turn, they gain:" +
                "@>@U: Whenever this SIGNI attacks, banish this SIGNI unless you pay %X %X %X."
        );

		setName("zh_simplified", "游月·红碧玉");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U 当这只精灵攻击时，如果不把%X %X %X:支付，那么这只精灵破坏。@@\n" +
                "@E %R%X %X %X %X:对战对手的精灵2只最多作为对象，直到回合结束时为止，这些得到\n" +
                "@>@U 当这只精灵攻击时，如果不把%X %X %X:支付，那么这只精灵破坏。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.YUZUKI);
        setColor(CardColor.RED);
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(4)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff), ChronoDuration.turnEnd());
        }

        private void onEnterEff2()
        {
            DataTable<CardIndex> targets = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().SIGNI());
            if(targets.get() != null) for(int i=0;i<targets.size();i++) attachAbility(targets.get(i), new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff), ChronoDuration.turnEnd());
        }

        private void onAttachedAutoEff()
        {
            CardIndex source = getAbility().getSourceCardIndex();
            if(!source.getIndexedInstance().payEner(Cost.colorless(3)))
            {
                source.getIndexedInstance().banish(source);
            }
        }
    }
}
