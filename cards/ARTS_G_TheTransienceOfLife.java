package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
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
import open.batoru.game.gfx.GFXZoneWall;

public final class ARTS_G_TheTransienceOfLife extends Card {

    public ARTS_G_TheTransienceOfLife()
    {
        setImageSets("WX25-P2-008", "WX25-P2-008U");

        setOriginalName("夢幻泡影");
        setAltNames("ミスティックソードボックス Misutikku Soodo Bokkusu Mystic Sword Box");
        setDescription("jp",
                "このアーツを使用する際、あなたのルリグデッキからアーツ１枚をルリグトラッシュに置いてもよい。そうした場合、このアーツの使用コストは%X %X %X減る。\n\n" +
                "このターン、あなたはパワー12000以上のシグニによってダメージを受けない。\n" +
                "&E４枚以上@0追加でシグニを２体まで対象とし、ターン終了時まで、それらのパワーを＋10000する。"
        );

        setName("en", "The Transience of Life");
        setDescription("en",
                "While using this ARTS, you may put 1 ARTS from your LRIG deck into the LRIG trash. If you do, the use cost of this ARTS is reduced by %X %X %X.\n\n" +
                "This turn, you can't be damaged by SIGNI with power 12000 or more." +
                "&E4 or more@0 Additionally, target up to 2 SIGNI, and until end of turn, they get +10000 power."
        );

        setName("zh_simplified", "梦幻泡影");
        setDescription("zh_simplified", 
                "这张必杀使用时，可以从你的分身牌组把必杀1张放置到分身废弃区。这样做的场合，这张必杀的使用费用减%X %X %X。\n" +
                "这个回合，你不会因为力量12000以上的精灵受到伤害。\n" +
                "&E4张以上@0追加精灵2只最多作为对象，直到回合结束时为止，这些的力量+10000。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(3));
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
            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
            GFXZoneWall.attachToChronoRecord(record, new GFXZoneWall(getOwner(), CardLocation.LIFE_CLOTH, "generic", new int[]{50,205,50}));
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_BE_DAMAGED, getOwner(), record, data ->
                CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) &&
                data.getSourceCardIndex().getIndexedInstance().getPower().getValue() >= 12000 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
            );
            
            if(getAbility().isRecollectFulfilled())
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.PLUS).SIGNI());
                gainPower(data, 10000, ChronoDuration.turnEnd());
            }
        }
    }
}
