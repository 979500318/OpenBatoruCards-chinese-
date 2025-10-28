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

public final class ARTS_G_PureHeartRighteousSpirit extends Card {

    public ARTS_G_PureHeartRighteousSpirit()
    {
        setImageSets("WX25-P1-007", "WX25-P1-007U");

        setOriginalName("心地光明");
        setAltNames("マジカルネイチャー Majikaru Neichaa Magical Nature");
        setDescription("jp",
                "&E４枚以上@0あなたのセンタールリグ１体を対象とし、ターン終了時まで、それは以下の能力を得る。" +
                "@>@A @[エクシード１]@：あなたのトラッシュから#Gを持たないカードを２枚まで対象とし、それらをエナゾーンに置く。\n" +
                "@A @[エクシード１]@：対戦相手のレベル３以上のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A @[エクシード２]@：【エナチャージ３】をする。その後、あなたのエナゾーンからカードを３枚まで対象とし、それらを手札に加える。"
        );

        setName("en", "Pure Heart, Righteous Spirit");
        setDescription("en",
                "&E4 or more@0 Target your center LRIG, and until end of turn, it gains:" +
                "@>@A @[Exceed 1]@: Target up to 2 cards without #G @[Guard]@ from your trash, and put them into the ener zone.\n" +
                "@A @[Exceed 1]@: Target 1 of your opponent's level 3 or higher SIGNI, and banish it.\n" +
                "@A @[Exceed 2]@: [[Ener Charge 3]]. Then, target up to 3 cards from your ener zone, and add them to your hand."
        );

        setName("zh_simplified", "心地光明");
        setDescription("zh_simplified", 
                "&E4张以上@0你的核心分身1只作为对象，直到回合结束时为止，其得到以下的能力。" +
                "@>@A @[超越 1]@:从你的废弃区把不持有#G的牌2张最多作为对象，将这些放置到能量区。\n" +
                "@A @[超越 1]@:对战对手的等级3以上的精灵1只作为对象，将其破坏。\n" +
                "@A @[超越 2]@:[[能量填充3]]。然后，从你的能量区把牌3张最多作为对象，将这些加入手牌。@@"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
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
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ENER).own().not(new TargetFilter().guard()).fromTrash());
            getAbility().getSourceCardIndex().getIndexedInstance().putInEner(data);
        }
        private void onAttachedActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(3,0)).get();
            getAbility().getSourceCardIndex().getIndexedInstance().banish(target);
        }
        private void onAttachedActionEff3()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().enerCharge(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.HAND).own().fromEner());
            getAbility().getSourceCardIndex().getIndexedInstance().addToHand(data);
        }
    }
}

