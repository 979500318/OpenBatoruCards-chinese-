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
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class ARTS_B_ShootingStar extends Card {

    public ARTS_B_ShootingStar()
    {
        setImageSets("WX25-P1-005", "WX25-P1-005U");

        setOriginalName("シューティング・スター");
        setAltNames("シューティングスター Shuutingu Sutaa");
        setDescription("jp",
                "&E４枚以上@0あなたのセンタールリグ１体を対象とし、ターン終了時まで、それは以下の能力を得る。" +
                "@>@A @[エクシード１]@：カードを４枚引き、手札を２枚捨てる。\n" +
                "@A @[エクシード１]@：対戦相手のシグニ１体を対象とし、対戦相手が手札を３枚捨てないかぎり、それをバニッシュする。\n" +
                "@A @[エクシード２]@：対戦相手のルリグ１体を対象とし、それを凍結する。対戦相手は手札を２枚捨てる。"
        );

        setName("en", "Shooting Star");
        setDescription("en",
                "&E4 or more@0 Target your center LRIG, and until end of turn, it gains:" +
                "@>@A @[Exceed 1]@: Draw 4 cards, and discard 2 cards from your hand.\n" +
                "@A @[Exceed 1]@: Target 1 of your opponent's SIGNI, and banish it unless your opponent discards 3 cards from their hand.\n" +
                "@A @[Exceed 2]@: Target 1 of your opponent's LRIG, and freeze it. Your opponent discards 2 cards from their hand."
        );

        setName("zh_simplified", "蓄爆·星光");
        setDescription("zh_simplified", 
                "&E4张以上@0你的核心分身1只作为对象，直到回合结束时为止，其得到以下的能力。" +
                "@>@A @[超越 1]@（从你的分身的下面把1张牌放置到分身废弃区）:抽4张牌，手牌2张舍弃。\n" +
                "@A @[超越 1]@:对战对手的精灵1只作为对象，如果对战对手不把手牌3张舍弃，那么将其破坏。\n" +
                "@A @[超越 2]@:对战对手的分身1只作为对象，将其冻结。对战对手把手牌2张舍弃。@@"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
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
            getAbility().getSourceCardIndex().getIndexedInstance().draw(4);
            getAbility().getSourceCardIndex().getIndexedInstance().discard(2);
        }
        private void onAttachedActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && discard(getOpponent(), 0,3, ChoiceLogic.BOOLEAN).size() != 3)
            {
                getAbility().getSourceCardIndex().getIndexedInstance().banish(target);
            }
        }
        private void onAttachedActionEff3()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
            getAbility().getSourceCardIndex().getIndexedInstance().freeze(target);
            
            getAbility().getSourceCardIndex().getIndexedInstance().discard(getOpponent(), 2);
        }
    }
}

