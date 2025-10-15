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
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class ARTS_R_DanceOfTheFlameSword extends Card {

    public ARTS_R_DanceOfTheFlameSword()
    {
        setImageSets("WX25-P1-003", "WX25-P1-003U");

        setOriginalName("炎剣之舞");
        setAltNames("エンケンノマイ Enken no Mai");
        setDescription("jp",
                "&E４枚以上@0あなたのセンタールリグ１体を対象とし、ターン終了時まで、それは以下の能力を得る。" +
                "@>@A @[エクシード１]@：対戦相手のセンタールリグがレベル３以上の場合、対戦相手のエナゾーンから、カード１枚と、それと共通する色を持たないカード１枚まで対象とし、それらをトラッシュに置く。\n" +
                "@A @[エクシード１]@：対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A @[エクシード２]@：対戦相手のライフクロス１枚をクラッシュする。"
        );

        setName("en", "Dance of the Flame Sword");
        setDescription("en",
                "&E4 or more@0 Target your center LRIG, and until end of turn, it gains:" +
                "@>@A @[Exceed 1]@: If your opponent's center LRIG is level 3 or higher, target 1 card and 1 card that doesn't share a common color with that card from your opponent's ener zone, and put them into the trash.\n" +
                "@A @[Exceed 1]@: Target 1 of your opponent's SIGNI with power 12000 or less, and banish it.\n" +
                "@A @[Exceed 2]@: Crush 1 of your opponent's life cloth."
        );

		setName("zh_simplified", "炎剑之舞");
        setDescription("zh_simplified", 
                "&E4张以上@0你的核心分身1只作为对象，直到回合结束时为止，其得到以下的能力。\n" +
                "@>@A @[超越 1]@:对战对手的核心分身在等级3以上的场合，从对战对手的能量区把，1张牌和，不持有与其共通颜色的牌1张最多作为对象，将这些放置到废弃区。\n" +
                "@A @[超越 1]@:对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n" +
                "@A @[超越 2]@对战对手的生命护甲1张击溃。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setUseTiming(UseTiming.MAIN);

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
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().LRIG()).get();

                ActionAbility attachedAct1 = new ActionAbility(new ExceedCost(1), this::onAttachedActionEff1);
                attachAbility(target, attachedAct1, ChronoDuration.turnEnd());

                ActionAbility attachedAct2 = new ActionAbility(new ExceedCost(1), this::onAttachedActionEff2);
                attachedAct2.setNestedDescriptionOffset(1);
                attachAbility(target, attachedAct2, ChronoDuration.turnEnd());

                ActionAbility attachedAct3 = new ActionAbility(new ExceedCost(2), this::onAttachedActionEff3);
                attachedAct3.setNestedDescriptionOffset(2);
                attachAbility(target, attachedAct3, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedActionEff1()
        {
            if(getLRIG(getOpponent()).getIndexedInstance().getLevel().getValue() >= 3)
            {
                DataTable<CardIndex> data = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner());
                if(data.get() != null)
                {
                    data.add(playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner().not(new TargetFilter().withColor(data.get().getIndexedInstance().getColor()))).get());
                    
                    trash(data);
                }
            }
        }
        private void onAttachedActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            getAbility().getSourceCardIndex().getIndexedInstance().banish(target);
        }
        private void onAttachedActionEff3()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().crush(getOpponent());
        }
    }
}

