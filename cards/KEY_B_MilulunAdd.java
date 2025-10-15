package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class KEY_B_MilulunAdd extends Card {

    public KEY_B_MilulunAdd()
    {
        setImageSets("WXK01-021");

        setOriginalName("ミルルン・アド");
        setAltNames("ミルルンアド Mirurun Ado");
        setDescription("jp",
                "@C：あなたのセンタールリグは以下の能力を得る。" +
                "@>@A $T1 @[エクシード２]@：あなたの手札からコストの合計が３以下のスペル１枚をコストを支払わずに使用する。@@" +
                "@E：あなたのトラッシュからスペル１枚を対象とし、それを手札に加える。\n" +
                "@A -S %B %X：スペル１つを対象とし、それの効果を打ち消す。"
        );

        setName("en", "Milulun Add");
        setDescription("en",
                "@C: Your center LRIG gains:" +
                "@>@A $T1 @[Exceed 2]@: Use 1 spell from your hand with a total cost of 3 or less without paying its cost.@@" +
                "@E: Target 1 spell from your trash, and add it to your hand.\n" +
                "@A -S %B %X: Target 1 spell, and cancel its effect."
        );

		setName("zh_simplified", "米璐璐恩·加");
        setDescription("zh_simplified", 
                "@C :你的核心分身得到以下的能力。\n" +
                "@>@A $T1 @[超越 2]@（从你的分身的下面把牌合计2张放置到分身废弃区）从你的手牌把费用的合计在3以下的魔法1张不把费用支付，使用。（不能无视限定条件）@@@@\n" +
                "@E :从你的废弃区把魔法1张作为对象，将其加入手牌。\n" +
                "@A 魔法切入%B%X:魔法1张作为对象，将其的效果取消。\n"
        );

        setType(CardType.KEY);
        setColor(CardColor.BLUE);
        setCost(Cost.coin(2));

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new TargetFilter().own().LRIG(), new AbilityGainModifier(this::onConstEffModGetSample));

            registerEnterAbility(this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1)), this::onActionEff);
            act.setActiveUseTiming(UseTiming.SPELLCUTIN);
        }

        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            ActionAbility attachedAct = cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(2), this::onAttachedActionEff);
            attachedAct.setUseLimit(UseLimit.TURN, 1);
            return attachedAct;
        }
        private void onAttachedActionEff()
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ACTIVATE).own().spell().withCost(0,3).fromHand()).get();
            if(cardIndex != null) use(cardIndex, new CostModifier(() -> new EnerCost(Cost.colorless(0)), ModifierMode.SET));
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().spell().fromTrash()).get();
            addToHand(target);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.CANCEL).OP().spell()).get();
            cancel(target);
        }
    }
}
