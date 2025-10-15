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
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class ARTS_W_WhiteWish extends Card {

    public ARTS_W_WhiteWish()
    {
        setImageSets("WX24-P2-002", "WX24-P2-002U");

        setOriginalName("ホワイト・ウィッシュ");
        setAltNames("ホワイトウィッシュ Howaito Uisshu");
        setDescription("jp",
                "このアーツを使用する際、あなたのルリグデッキから白のアーツ１枚をルリグトラッシュに置いてもよい。そうした場合、このアーツの使用コストは%W %W %W減る。\n\n" +
                "対戦相手のシグニ１体と、そのシグニと同じレベルの対戦相手のルリグ１体を対象とし、ターン終了時まで、それらは@>@C：アタックできない。@@を得る。"
        );

        setName("en", "White Wish");
        setDescription("en",
                "While using this ARTS, you may put 1 white ARTS from your LRIG deck into the LRIG trash. If you do, the use cost of this ARTS is reduced by %W %W %W.\n\n" +
                "Target 1 of your opponent's SIGNI and 1 of your opponent's LRIG with the same level as that SIGNI, and until end of turn, they gain:" +
                "@>@C: Can't attack."
        );

		setName("zh_simplified", "纯白·愿望");
        setDescription("zh_simplified", 
                "这张必杀使用时，可以从你的分身牌组把白色的必杀1张放置到分身废弃区。这样做的场合，这张必杀的使用费用减%W %W %W。\n" +
                "对战对手的精灵1只和，与那只精灵相同等级的对战对手的分身1只作为对象，直到回合结束时为止，这些得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 3));
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
            arts.setReductionCost(new TrashCost(new TargetFilter().own().ARTS().withColor(CardColor.WHITE).except(getCardIndex()).fromLocation(CardLocation.DECK_LRIG)), Cost.color(CardColor.WHITE, 3));
        }
        
        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI());
            if(data.get() != null)
            {
                data.add(playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().anyLRIG().withLevel(data.get().getIndexedInstance().getLevel().getValue())).get());

                for(int i=0;i<data.size();i++) attachAbility(data.get(i), new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            }
        }
    }
}
