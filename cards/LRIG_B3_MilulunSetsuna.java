package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

import java.util.List;

public final class LRIG_B3_MilulunSetsuna extends Card {

    public LRIG_B3_MilulunSetsuna()
    {
        setImageSets("WXDi-P10-007", "WXDi-P10-007U");

        setOriginalName("ミルルン・セツナ");
        setAltNames("ミルルンセツナ Mirurun Setsuna");
        setDescription("jp",
                "@C：対戦相手のスペルの使用コストは%X増える。\n" +
                "@A $T1 %B @[シグニ１枚とスペル１枚を捨てる]@：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A $G1 %B0：あなたのデッキの上からカードを１０枚見る。その中からコストの合計が４以下になるようにスペルを２枚までチェックゾーンに置き、残りをデッキに加えてシャッフルする。この方法でチェックゾーンに置いたスペルを好きな順番でコストを支払わずに使用する。"
        );

        setName("en", "Milulun Setsuna");
        setDescription("en",
                "@C: Use costs of your opponent's spells are increased by %X.\n" +
                "@A $T1 %B @[Discard a SIGNI and a spell]@: Vanish target SIGNI on your opponent's field.\n" +
                "@A $G1 %B0: Look at the top ten cards of your deck. Put up to two spells from among them with a total cost less than or equal to four into the Check Zone, and shuffle the rest into your deck. Use the spells put into the Check Zone this way in any order without paying their cost."
        );

        setName("en_fan", "Milulun Setsuna");
        setDescription("en_fan",
                "@C: The use costs of your opponent's spells are increased by %X.\n" +
                "@A $T1 %B @[Discard 1 spell and 1 SIGNI from your hand]@: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@A $G1 %B0: Look at the top 10 cards of your deck. Put up to 2 spells with total cost of 4 or less from among them onto your check zone, and shuffle the rest into your deck. Use the spells put into your check zone this way in any order without paying their costs."
        );

		setName("zh_simplified", "米璐璐恩·刹");
        setDescription("zh_simplified", 
                "@C :对战对手的魔法的使用费用增%X。\n" +
                "@A $T1 %B精灵1张和魔法1张舍弃:对战对手的精灵1只作为对象，将其破坏。\n" +
                "@A $G1 %B0:从你的牌组上面看10张牌。从中把费用的合计在4以下的魔法2张最多放置到检查区，剩下的加入牌组洗切。这个方法放置到检查区的魔法任意顺序，不把费用支付，使用。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MILULUN);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new TargetFilter().OP().spell().anyLocation(),
                new CostModifier(() -> new EnerCost(Cost.colorless(1)), ModifierMode.INCREASE)
            );

            ActionAbility act1 = registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.BLUE, 1)),
                new DiscardCost(new TargetFilter().spell()),
                new DiscardCost(new TargetFilter().SIGNI())
            ), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }

        private void onActionEff2()
        {
            look(10);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().spell().fromLooked(), this::onActionEff2TargetCond);
            putInCheckZone(data);
            
            returnToDeck(getCardsInLooked(getOwner()), DeckPosition.TOP);
            shuffleDeck();
            
            use(data, new CostModifier(() -> new EnerCost(Cost.colorless(0)), ModifierMode.SET));
        }
        private boolean onActionEff2TargetCond(List<CardIndex> listPickedCards)
        {
            int sum = 0;
            for(CardIndex cardIndex : listPickedCards) sum += Cost.getOriginalCostAsNumber(cardIndex.getCardReference());

            return sum <= 4;
        }
    }
}
