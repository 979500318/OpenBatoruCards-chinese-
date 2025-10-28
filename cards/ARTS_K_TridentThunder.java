package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.cost.TrashCost;

import java.util.Objects;

public final class ARTS_K_TridentThunder extends Card {

    public ARTS_K_TridentThunder()
    {
        setImageSets("WX25-P2-010", "WX25-P2-010U");

        setOriginalName("トライデント・サンダー");
        setAltNames("トライデントサンダー Toraidento Sandaa");
        setDescription("jp",
                "このアーツを使用する際、あなたのルリグデッキからアーツ１枚をルリグトラッシュに置いてもよい。そうした場合、このアーツの使用コストは%X %X %X減る。\n\n" +
                "対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらのパワーをそれぞれ－5000する。\n" +
                "&E４枚以上@0追加で、このターン、対戦相手は表記されているパワーと異なるパワーのシグニでアタックできない。"
        );

        setName("en", "Trident Thunder");
        setDescription("en",
                "While using this ARTS, you may put 1 ARTS from your LRIG deck into the LRIG trash. If you do, the use cost of this ARTS is reduced by %X %X %X.\n\n" +
                "Target up to 2 of your opponent's SIGNI, and until end of turn, they get --5000 power.\n" +
                "&E4 or more@0 Additionally, this turn, your opponent can't attack with SIGNI with power different from their printed power."
        );

        setName("zh_simplified", "三叉·疾雷");
        setDescription("zh_simplified", 
                "这张必杀使用时，可以从你的分身牌组把必杀1张放置到分身废弃区。这样做的场合，这张必杀的使用费用减%X %X %X。\n" +
                "对战对手的精灵2只最多作为对象，直到回合结束时为止，这些的力量各-5000。\n" +
                "&E4张以上@0追加，这个回合，对战对手的与正面记载的力量不同力量的精灵不能攻击。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(3));
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
            DataTable<CardIndex> targets = playerTargetCard(0,2, new TargetFilter(TargetHint.MINUS).OP().SIGNI());
            gainPower(targets, -5000, ChronoDuration.turnEnd());
            
            if(getAbility().isRecollectFulfilled())
            {
                addPlayerRuleCheck(PlayerRuleCheckType.CAN_ATTACK, getOpponent(), ChronoDuration.turnEnd(), data ->
                    CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) &&
                    !Objects.equals(data.getSourceCardIndex().getIndexedInstance().getPower().getValue(), data.getSourceCardIndex().getCardReference().getPower()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
                );
            }
        }
    }
}
