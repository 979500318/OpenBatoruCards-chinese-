package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class ARTS_K_PurpleDaze extends Card {

    public ARTS_K_PurpleDaze()
    {
        setImageSets("WX24-P3-010", "WX24-P3-010U");

        setOriginalName("パープル・デイズ");
        setAltNames("パープル・デイズ Paapuru Deizu");
        setDescription("jp",
                "このアーツを使用する際、あなたのシグニを好きな数場からトラッシュに置く。このアーツの使用コストはこの方法でトラッシュに置いたシグニ１体につき%K減る。\n\n" +
                "&E４枚以上@0ターン終了時まで、対戦相手のすべてのシグニは@>@U：このシグニがアタックしたとき、ターン終了時まで、このシグニのパワーを－15000する。@@を得る。"
        );

        setName("en", "Purple Daze");
        setDescription("en",
                "While using this ARTS, you may put any number of SIGNI from your field into the trash. The use cost of this ARTS is reduced by %K for each SIGNI put into the trash this way.\n\n" +
                "&E4 or more@0 Until end of turn, all of your opponent's SIGNI gain:" +
                "@>@U: Whenever this SIGNI attacks, until end of turn, it gets --15000 power."
        );

        setName("zh_simplified", "紫色·韶华");
        setDescription("zh_simplified", 
                "这张必杀使用时，你的精灵任意数量从场上放置到废弃区。这张必杀的使用费用依据这个方法放置到废弃区的精灵的数量，每有1只就减%K。\n" +
                "&E4张以上@0直到回合结束时为止，对战对手的全部的精灵得到" +
                "@>@U :当这只精灵攻击时，直到回合结束时为止，这只精灵的力量-15000。@@"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 4));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setCondition(this::onARTSEffCond);
            arts.setReductionCost(new TrashCost(0,AbilityConst.MAX_UNLIMITED, new TargetFilter().own().SIGNI()), Cost.color(CardColor.BLACK, 1));
            arts.setRecollect(4);
        }

        private ConditionState onARTSEffCond()
        {
            return arts.isRecollectFulfilled() ? ConditionState.OK : ConditionState.WARN;
        }
        private void onARTSEff()
        {
            if(arts.isRecollectFulfilled())
            {
                forEachSIGNIOnField(getOpponent(), cardIndex -> {
                    AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                    attachAbility(cardIndex, attachedAuto, ChronoDuration.turnEnd());
                });
            }
        }
        private void onAttachedAutoEff()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().gainPower(getAbility().getSourceCardIndex(), -15000, ChronoDuration.turnEnd());
        }
    }
}
