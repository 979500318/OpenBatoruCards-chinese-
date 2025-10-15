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
import open.batoru.data.ability.AbilityConst.PrintedValue;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class ARTS_K_RecollectionCreate extends Card {

    public ARTS_K_RecollectionCreate()
    {
        setImageSets("WX25-P1-009", "WX25-P1-009U");

        setOriginalName("リコレクション・クリエイト");
        setAltNames("リコレクションクリエイト Rikorekushon Kurieito");
        setDescription("jp",
                "&E４枚以上@0あなたのセンタールリグ１体を対象とし、ターン終了時まで、それは以下の能力を得る。" +
                "@>@A @[エクシード１]@：ターン終了時まで、対戦相手のすべてのシグニのパワーを－3000する。対戦相手のデッキの上からカードを３枚トラッシュに置く。\n" +
                "@A @[エクシード１]@：表記されているパワーと異なるパワーの対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A @[エクシード２]@：あなたのトラッシュからシグニを３枚まで対象とし、それらを場に出す。"
        );

        setName("en", "Recollection Create");
        setDescription("en",
                "&E4 or more@0 Target your center LRIG, and until end of turn, it gains:" +
                "@>@A @[Exceed 1]@: Until end of turn, all of your opponent's SIGNI get --3000 power. Put the top 3 cards of your opponent's deck into the trash.\n" +
                "@A @[Exceed 1]@: Target 1 of your opponent's SIGNI with power different from its printed power, and banish it.\n" +
                "@A @[Exceed 2]@: Target up to 3 SIGNI from your trash, and put them onto the field."
        );

		setName("zh_simplified", "溯忆·创生");
        setDescription("zh_simplified", 
                "&E4张以上@0你的核心分身1只作为对象，直到回合结束时为止，其得到以下的能力。\n" +
                "@>@A @[超越 1]@:直到回合结束时为止，对战对手的全部的精灵的力量-3000。从对战对手的牌组上面把3张牌放置到废弃区。\n" +
                "@A @[超越 1]@:与正面记载的力量不同力量的对战对手的精灵1只作为对象，将其破坏。\n" +
                "@A @[超越 2]@从你的废弃区把精灵3张最多作为对象，将这些出场。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
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
            getAbility().getSourceCardIndex().getIndexedInstance().gainPower(getSIGNIOnField(getOpponent()), -3000, ChronoDuration.turnEnd());
            getAbility().getSourceCardIndex().getIndexedInstance().millDeck(getOpponent(), 3);
        }
        private void onAttachedActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPrintedPower(PrintedValue.LOWER_THAN_CURRENT, PrintedValue.HIGHER_THAN_CURRENT)).get();
            getAbility().getSourceCardIndex().getIndexedInstance().banish(target);
        }
        private void onAttachedActionEff3()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable());
            getAbility().getSourceCardIndex().getIndexedInstance().putOnField(data);
        }
    }
}

