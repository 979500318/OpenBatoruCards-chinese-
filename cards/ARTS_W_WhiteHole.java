package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class ARTS_W_WhiteHole extends Card {

    public ARTS_W_WhiteHole()
    {
        setImageSets("WX25-P2-002", "WX25-P2-002U");

        setOriginalName("ホワイト・ホール");
        setAltNames("ホワイトホール Howaito Hooru");
        setDescription("jp",
                "このアーツを使用する際、あなたのルリグデッキからアーツ１枚をルリグトラッシュに置いてもよい。そうした場合、このアーツの使用コストは%X %X %X減る。\n\n" +
                "ターン終了時まで、対戦相手のすべてのシグニは能力を失う。\n" +
                "&E４枚以上@0その後、追加で対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらは@>@C：アタックできない。@@を得る。"
        );

        setName("en", "White Hole");
        setDescription("en",
                "While using this ARTS, you may put 1 ARTS from your LRIG deck into the LRIG trash. If you do, the use cost of this ARTS is reduced by %X %X %X.\n\n" +
                "Until end of turn, all of your opponent's SIGNI lose their abilities.\n" +
                "&E4 or more@0 Then, additionally, target up to 2 of your opponent's SIGNI, and until end of turn, they gain:" +
                "@>@C: Can't attack."
        );

		setName("zh_simplified", "纯白·之洞");
        setDescription("zh_simplified", 
                "这张必杀使用时，可以从你的分身牌组把必杀1张放置到分身废弃区。这样做的场合，这张必杀的使用费用减%X %X %X。\n" +
                "直到回合结束时为止，对战对手的全部的精灵的能力失去。\n" +
                "&E4张以上@0然后，追加对战对手的精灵2只最多作为对象，直到回合结束时为止，这些得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(4));
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
            
            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setReductionCost(new TrashCost(new TargetFilter().own().ARTS().except(cardId).fromLocation(CardLocation.DECK_LRIG)), Cost.colorless(3));
            arts.setRecollect(4);
        }

        private void onARTSEff()
        {
            disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            
            if(getAbility().isRecollectFulfilled())
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().SIGNI());
                if(data.get() != null) for(int i=0;i<data.size();i++) attachAbility(data.get(i), new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            }
        }
    }
}
